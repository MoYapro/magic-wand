package de.moyapro.colors

import android.content.*
import android.os.*
import android.util.*
import androidx.activity.*
import androidx.activity.compose.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.layout.*
import androidx.datastore.core.*
import androidx.datastore.preferences.*
import androidx.datastore.preferences.core.*
import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.fight.*
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

    private val gameViewModel: GameViewModel by viewModels {
        GameViewModelFactory(this.dataStore)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val currentGameStateResult: Result<GameState> by gameViewModel.uiState.collectAsState()
            val gameState = currentGameStateResult.getOrThrow()
            ColorsTheme {
                val menuActions: List<MenuEntryInfo> = determinMenuEntries(gameState)
                Column {
                    Text(modifier = Modifier.layoutId(10000), text = "newGameState: ${gameState.currentFight.fightState}")
                    MainMenu(menuActions)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val currentGameState = gameViewModel.getCurrentGameState()
        Log.i("resume main activity", getConfiguredJson().writeValueAsString(currentGameState))
    }

    private fun determinMenuEntries(gameState: GameState?): List<MenuEntryInfo> {
        val menuActions: MutableList<MenuEntryInfo> = mutableListOf()
        menuActions.add("Set state to fight started" to { gameViewModel.addAction(StartFightAction()) })
        menuActions.add("Reset all progress" to ::initNewGame)
        if (gameState?.currentFight?.fightState == ONGOING) {
            menuActions.add("Continue fight" to ::startFightActivity)
        }
        menuActions.add("Prepare next fight" to ::startLootActivity)
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
