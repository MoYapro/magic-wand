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
import de.moyapro.colors.game.model.*
import de.moyapro.colors.ui.theme.*
import de.moyapro.colors.ui.view.mainmenu.*
import de.moyapro.colors.util.*
import de.moyapro.colors.util.FightOutcome.ONGOING
import de.moyapro.colors.util.FightOutcome.WIN
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "gameSaveState")


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
//        initNewGame()
//        startLootActivity()
        val (fightState, wandState, mageState) = loadSavedState()
        super.onCreate(savedInstanceState)
        setContent {
            ColorsTheme {
                val menuActions: MutableList<Pair<String, () -> Unit>> = mutableListOf()
                if (fightState != null) {
                    if (fightState.fightHasEnded == WIN) {
                        menuActions.add("Next fight" to ::startFightActivity)
                    }
                    if (fightState.fightHasEnded == ONGOING && fightState.wands.mapNotNull(Wand::mageId).isNotEmpty()) {
                        menuActions.add("Continue fight" to ::startFightActivity)
                    }
                    menuActions.add("Start all over" to ::initNewGame)
                } else {
                    menuActions.add("New game" to ::initNewGame)
                }
                menuActions.add("Loot" to ::startLootActivity)
                Column {

                    Text(text = "fightState: ${fightState?.fightHasEnded}")
                    MainMenu(
                        menuActions
                    )
                }
            }
        }
    }


    private fun loadSavedState(): Triple<MyGameState?, List<Wand>?, Unit> = runBlocking {
        dataStore.data.map { preferences ->
            val objectMapper = getConfiguredJson()
            Triple<MyGameState?, List<Wand>?, Unit>(
                objectMapper.readValue(preferences[FIGHT_STATE]),
                objectMapper.readValue(preferences[WAND_STATE]),
                //objectMapper.readValue(preferences[MAGE_STATE],
                Unit
            )
        }.first()
    }

    private inline fun <reified T> ObjectMapper.readValue(value: String?): T? {
        if (null == value) return null
        return this.readValue(value, T::class.java)
    }

    private fun initNewGame() = runBlocking {
        val initialGameState = StartFightFactory.setupFightStage()
        saveFightState(initialGameState)
        saveWands(initialGameState.wands)
    }

    private fun saveFightState(gameState: MyGameState): Unit = runBlocking {
        dataStore.edit { settings ->
            settings[FIGHT_STATE] = getConfiguredJson().writeValueAsString(gameState)
        }
    }

    private fun saveWands(wands: List<Wand>): Unit = runBlocking {
        dataStore.edit { settings ->
            settings[WAND_STATE] = getConfiguredJson().writeValueAsString(wands)
        }
    }

    private fun startLootActivity() {
        this.startActivity(Intent(this, LootActivity::class.java))
    }

    private fun startFightActivity() {
        this.startActivity(Intent(this, FightActivity::class.java))
    }
}
