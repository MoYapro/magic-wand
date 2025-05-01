package de.moyapro.colors

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.fasterxml.jackson.module.kotlin.readValue
import de.moyapro.colors.game.GameViewModel
import de.moyapro.colors.game.GameViewModelFactory
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.actions.NoOp
import de.moyapro.colors.game.actions.fight.EndTurnAction
import de.moyapro.colors.game.actions.fight.PlaceMagicAction
import de.moyapro.colors.game.actions.fight.ShowTargetSelectionAction
import de.moyapro.colors.game.actions.fight.StartFightAction
import de.moyapro.colors.game.actions.fight.TargetSelectedAction
import de.moyapro.colors.game.actions.fight.ZapAction
import de.moyapro.colors.game.generators.Initializer
import de.moyapro.colors.game.model.FieldId
import de.moyapro.colors.game.model.SlotId
import de.moyapro.colors.game.model.WandId
import de.moyapro.colors.game.model.gameState.FightData
import de.moyapro.colors.game.model.gameState.GameOptions
import de.moyapro.colors.game.model.gameState.GameState
import de.moyapro.colors.game.model.gameState.ProgressionData
import de.moyapro.colors.game.model.gameState.RunData
import de.moyapro.colors.game.persistance.save
import de.moyapro.colors.game.persistance.saveActions
import de.moyapro.colors.util.CURRENT_FIGHT_STATE_KEY
import de.moyapro.colors.util.CURRENT_RUN_STATE_KEY
import de.moyapro.colors.util.FightState
import de.moyapro.colors.util.GAME_ACTIONS_KEY
import de.moyapro.colors.util.GAME_OPTIONS_STATE_KEY
import de.moyapro.colors.util.OVERALL_PROGRESSION_STATE_KEY
import de.moyapro.colors.util.getConfiguredJson
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

@OptIn(ExperimentalCoroutinesApi::class)
class LoadSaveTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher + Job())

    @get:Rule
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    private val testDataStore: DataStore<Preferences> = PreferenceDataStoreFactory.create(scope = testScope, produceFile = { tmpFolder.newFile("user.preferences_pb") })

    @Test
    fun saveActionsTest(): Unit = runBlocking {
        val actions = listOf(
            PlaceMagicAction(
                magicToPlace = createExampleMagic(), slotId = SlotId(), wandId = WandId()
            ),
            ZapAction(WandId()),
            NoOp(),
            EndTurnAction(),
            TargetSelectedAction(targetFieldId = FieldId(0)),
            ShowTargetSelectionAction(originalAction = ZapAction(wandId = WandId())),
        )
        saveActions(testDataStore, actions)

        val savedActionsString = testDataStore.data.first()[GAME_ACTIONS_KEY]
        savedActionsString shouldNotBe null
        val loadedActions: List<GameAction> = getConfiguredJson().readValue(savedActionsString!!)
        loadedActions shouldBe actions
    }

    @Test
    fun materializeActions(): Unit = runBlocking {
        val emptyActions = emptyList<GameAction>()
        val initialGameState: GameState = Initializer.createInitialGameState()
        save(testDataStore, initialGameState, emptyActions)
        val gameViewModel: GameViewModel = GameViewModelFactory(testDataStore).create(GameViewModel::class.java)
        getConfiguredJson().readValue<FightData>(testDataStore.data.first()[CURRENT_FIGHT_STATE_KEY]!!).fightState shouldBe initialGameState.currentFight.fightState
        gameViewModel.uiState.value.getOrThrow() shouldBe initialGameState
        gameViewModel.addAction(StartFightAction())
        testDataStore.data.first()[GAME_ACTIONS_KEY] shouldBe """[{"randomSeed":-1,"name":"Increase action counter","target":null,"@type":"IncreaseActionCounterAction"},{"randomSeed":1,"name":"Start fight Action","target":null,"@type":"StartFightAction"}]"""
        gameViewModel.uiState.value.getOrThrow().currentFight.fightState shouldBe FightState.ONGOING
        getConfiguredJson().readValue<FightData>(testDataStore.data.first()[CURRENT_FIGHT_STATE_KEY]!!).fightState shouldBe FightState.NOT_STARTED

        gameViewModel.materializeActions()

        gameViewModel.uiState.value.getOrThrow().currentFight.fightState shouldBe FightState.ONGOING
        getConfiguredJson().readValue<FightData>(testDataStore.data.first()[CURRENT_FIGHT_STATE_KEY]!!) shouldBe gameViewModel.uiState.value.getOrThrow().currentFight
        getConfiguredJson().readValue<RunData>(testDataStore.data.first()[CURRENT_RUN_STATE_KEY]!!) shouldBe gameViewModel.uiState.value.getOrThrow().currentRun
        getConfiguredJson().readValue<GameOptions>(testDataStore.data.first()[GAME_OPTIONS_STATE_KEY]!!) shouldBe gameViewModel.uiState.value.getOrThrow().options
        getConfiguredJson().readValue<ProgressionData>(testDataStore.data.first()[OVERALL_PROGRESSION_STATE_KEY]!!) shouldBe gameViewModel.uiState.value.getOrThrow().progression

        testDataStore.data.first()[GAME_ACTIONS_KEY] shouldBe """[]"""
    }


    @Test
    fun `start fight action only once`(): Unit = runBlocking {
        val actions: List<GameAction> = listOf(StartFightAction())
        val initialGameState: GameState = Initializer.createInitialGameState()
        save(testDataStore, initialGameState, actions)
        val gameViewModel: GameViewModel = GameViewModelFactory(testDataStore).create(GameViewModel::class.java)
        shouldThrow<Exception> { gameViewModel.addAction(StartFightAction()) }
    }
}