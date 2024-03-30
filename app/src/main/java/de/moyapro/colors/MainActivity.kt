package de.moyapro.colors

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.datastore.preferences.core.edit
import com.fasterxml.jackson.databind.ObjectMapper
import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.game.StartFightFactory
import de.moyapro.colors.takeTwo.Wand
import de.moyapro.colors.ui.theme.ColorsTheme
import de.moyapro.colors.util.FIGHT_STATE
import de.moyapro.colors.util.FightOutcome.ONGOING
import de.moyapro.colors.util.WAND_STATE
import de.moyapro.colors.util.getConfiguredJson
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        val (fightState, wandState, mageState) = loadSavedState()
        super.onCreate(savedInstanceState)
        setContent {
            ColorsTheme {
                val menuActions: MutableList<Pair<String, () -> Unit>> = mutableListOf()
                if (fightState != null) {
                    if (fightState.fightHasEnded != ONGOING) {
                        menuActions.add("Edit wands" to ::startEditWandsActivity)
                        menuActions.add("Next fight" to ::startFightActivity)
                    }
                    if (fightState.fightHasEnded == ONGOING) {
                        menuActions.add("Continue fight" to ::startFightActivity)
                    }
                    menuActions.add("Start all over" to ::initNewGame)
                } else {
                    menuActions.add("New game" to ::initNewGame)
                }
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
        val (_, wands, _) = loadSavedState()
        check(wands == null || wands.isNotEmpty()) { "Cannot start with no wands" }
        val initialGameState = StartFightFactory.createInitialState(wands)
        saveFightState(initialGameState)
        if (null == wands) {
            saveWands(initialGameState.wands)
        }
        startFightActivity()
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


    private fun startFightActivity() {
        this.startActivity(Intent(this, FightActivity::class.java))
    }

    private fun startEditWandsActivity() {
        this.startActivity(Intent(this, EditWandsActivity::class.java))
    }

}
