package de.moyapro.colors

import androidx.datastore.core.*
import androidx.datastore.preferences.core.*
import com.fasterxml.jackson.module.kotlin.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.actions.fight.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.persistance.*
import de.moyapro.colors.util.*
import io.kotest.matchers.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.rules.*

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
        val mageId = MageId(1)
        val actions = listOf(
            PlaceMagicAction(
                magicToPlace = createExampleMagic(),
                slotId = SlotId(),
                wandId = WandId()
            ),
            ZapAction(WandId()),
            NoOp(),
            EndTurnAction(),
            TargetSelectedAction(target = EnemyId()),
            ShowTargetSelectionAction(originalAction = ZapAction(wandId = WandId())),
            HitMageAction(damage = Int.MAX_VALUE, targetMageId = MageId()),
        )
        saveActions(testDataStore, actions)

        val savedActionsString = testDataStore.data.first()[GAME_ACTIONS_KEY]
        savedActionsString shouldNotBe null
        val loadedActions: List<GameAction> = getConfiguredJson().readValue(savedActionsString!!)
        loadedActions shouldBe actions
    }
}