package de.moyapro.colors

import android.content.*
import android.os.*
import androidx.activity.*
import androidx.activity.compose.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.datastore.core.*
import androidx.datastore.preferences.*
import androidx.datastore.preferences.core.*
import com.fasterxml.jackson.databind.*
import de.moyapro.colors.game.generators.*
import de.moyapro.colors.game.model.gameState.*
import de.moyapro.colors.ui.theme.*
import de.moyapro.colors.ui.view.mainmenu.*
import de.moyapro.colors.util.*
import de.moyapro.colors.util.FightState.NOT_STARTED
import de.moyapro.colors.util.FightState.ONGOING
import de.moyapro.colors.util.FightState.WIN
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "gameSaveState")


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        val newGameState = loadSavedState()
        super.onCreate(savedInstanceState)
        setContent {
            ColorsTheme {
                val menuActions: List<MenuEntryInfo> = determinMenuEntries(newGameState)
                Column {
                    Text(text = "newGameState: ${newGameState.currentFight.fightState}")
                    MainMenu(menuActions)
                }
            }
        }
    }

    private fun determinMenuEntries(newGameState: NewGameState?): List<MenuEntryInfo> {
        val menuActions: MutableList<MenuEntryInfo> = mutableListOf()
        if (newGameState?.currentFight != null) {
            if (newGameState.currentFight.fightState == WIN) {
                menuActions.add("Next fight" to ::startFightActivity)
            }
            if (newGameState.currentFight.fightState == ONGOING) {
                menuActions.add("Continue fight" to ::startFightActivity)
            }
            menuActions.add("Start all over" to ::initNewGame)
        } else {
            menuActions.add("New game" to ::initNewGame)
        }
        menuActions.add("Loot" to ::startLootActivity)
        return menuActions
    }


    private fun loadSavedState(): NewGameState = runBlocking {
        dataStore.data.map { preferences ->
            val objectMapper = getConfiguredJson()
            val currentFight: FightData? = objectMapper.readValue(preferences[CURRENT_FIGHT_STATE_KEY])
            val currentRun: RunData? = objectMapper.readValue(preferences[CURRENT_RUN_STATE_KEY])
            val progression: ProgressionData? = objectMapper.readValue(preferences[OVERALL_PROGRESSION_STATE_KEY])
            val options: GameOptions? = objectMapper.readValue(preferences[GAME_OPTIONS_STATE_KEY])
            return@map NewGameState(
                currentFight = currentFight ?: initEmptyFight(),
                currentRun = currentRun ?: initEmptyRun(),
                progression = progression ?: initEmptyProgression(),
                options = options ?: initDefaultOptions()
            )
        }.first()
    }

    private fun initDefaultOptions(): GameOptions {
        return GameOptions(
            thisIsAnOption = true
        )
    }

    private fun initEmptyProgression(): ProgressionData {
        return ProgressionData(
            unlockedWands = emptyList(),
            achievements = emptyList(),
            unlockedSpells = emptyList(),
            unlockedEnemies = emptyList()
        )
    }

    private inline fun <reified T> ObjectMapper.readValue(value: String?): T? {
        if (null == value) return null
        return this.readValue(value, T::class.java)
    }

    private fun initNewGame() = runBlocking {
        val initialGameState = StartFightFactory.setupFightStage()
        save(initialGameState)
    }

    private fun save(gameState: NewGameState): Unit = runBlocking {
        dataStore.edit { settings ->
            settings[CURRENT_FIGHT_STATE_KEY] = getConfiguredJson().writeValueAsString(gameState.currentFight)
            settings[CURRENT_RUN_STATE_KEY] = getConfiguredJson().writeValueAsString(gameState.currentRun)
            settings[OVERALL_PROGRESSION_STATE_KEY] = getConfiguredJson().writeValueAsString(gameState.progression)
            settings[GAME_OPTIONS_STATE_KEY] = getConfiguredJson().writeValueAsString(gameState.options)
        }
    }

    private fun startLootActivity() {
        this.startActivity(Intent(this, LootActivity::class.java))
    }

    private fun startFightActivity() {
        this.startActivity(Intent(this, FightActivity::class.java))
    }

    private fun initEmptyFight(): FightData {
        return FightData(
            currentTurn = 0,
            fightState = NOT_STARTED,
            battleBoard = BattleBoard(fields = emptyList()),
            mages = emptyList(),
            magicToPlay = emptyList(),
            wands = emptyList()
        )
    }

    private fun initEmptyRun(): RunData {
        return RunData(
            mages = emptyList(),
            activeWands = emptyList(),
            spells = emptyList(),
            wandsInBag = emptyList()
        )
    }
}
