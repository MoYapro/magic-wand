package de.moyapro.colors

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.fasterxml.jackson.module.kotlin.readValue
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.actions.NoOp
import de.moyapro.colors.game.actions.fight.EndTurnAction
import de.moyapro.colors.game.actions.fight.PlaceMagicAction
import de.moyapro.colors.game.actions.fight.ShowTargetSelectionAction
import de.moyapro.colors.game.actions.fight.TargetSelectedAction
import de.moyapro.colors.game.actions.fight.ZapAction
import de.moyapro.colors.game.model.FieldId
import de.moyapro.colors.game.model.SlotId
import de.moyapro.colors.game.model.WandId
import de.moyapro.colors.game.persistance.saveActions
import de.moyapro.colors.util.GAME_ACTIONS_KEY
import de.moyapro.colors.util.getConfiguredJson
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

    private val testDataStore: DataStore<Preferences> = PreferenceDataStoreFactory.create(
        scope = testScope,
        produceFile = { tmpFolder.newFile("user.preferences_pb") }
    )

    @Test
    fun saveActionsTest(): Unit = runBlocking {
        val actions = listOf(
            PlaceMagicAction(
                magicToPlace = createExampleMagic(),
                slotId = SlotId(),
                wandId = WandId()
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
}