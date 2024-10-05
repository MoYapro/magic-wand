package de.moyapro.colors

import android.content.*
import android.os.*
import androidx.activity.*
import androidx.activity.compose.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.*
import androidx.compose.ui.layout.*
import androidx.datastore.core.*
import androidx.datastore.preferences.*
import androidx.datastore.preferences.core.*
import de.moyapro.colors.game.generators.*
import de.moyapro.colors.game.model.gameState.*
import de.moyapro.colors.game.persistance.*
import de.moyapro.colors.ui.theme.*
import de.moyapro.colors.ui.view.mainmenu.*
import de.moyapro.colors.util.*
import de.moyapro.colors.util.FightState.ONGOING
import kotlinx.coroutines.*

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "gameSaveState")


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gameState: GameState = runBlocking { loadSavedState(dataStore) }
        setContent {
            ColorsTheme {
                val menuActions: List<MenuEntryInfo> = determinMenuEntries(gameState)
                Column {
                    Text(modifier = Modifier.layoutId(10000), text = "newGameState: ${gameState.currentFight.fightState}")
                    MainMenu(menuActions)
                }
            }
        }
    }

    private fun determinMenuEntries(gameState: GameState?): List<MenuEntryInfo> {
        val menuActions: MutableList<MenuEntryInfo> = mutableListOf()
        menuActions.add("Start all over" to ::initNewGame)
        if (gameState?.currentFight != null) {
            if (gameState.currentFight.fightState != ONGOING) {
                menuActions.add("Next fight" to ::startFightActivity)
            }
            if (gameState.currentFight.fightState == ONGOING) {
                menuActions.add("Continue fight" to ::startFightActivity)
            }
        } else {
            menuActions.add("New game" to ::initNewGame)
        }
        menuActions.add("Loot" to ::startLootActivity)
        return menuActions
    }

    private fun initNewGame() = runBlocking {
        val initialGameState = Initializer.createInitialGameState()
        save(dataStore, initialGameState)
    }


    private fun startLootActivity() {
        this.startActivity(Intent(this, LootActivity::class.java))
    }

    private fun startFightActivity() {
        this.startActivity(Intent(this, FightActivity::class.java))
    }


}
