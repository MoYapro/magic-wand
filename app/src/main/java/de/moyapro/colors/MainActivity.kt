package de.moyapro.colors

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import de.moyapro.colors.game.GameViewModel
import de.moyapro.colors.game.GameViewModelFactory
import de.moyapro.colors.game.generators.Initializer
import de.moyapro.colors.game.model.gameState.GameState
import de.moyapro.colors.game.persistance.save
import de.moyapro.colors.ui.theme.ColorsTheme
import de.moyapro.colors.ui.view.mainmenu.MainMenu
import de.moyapro.colors.util.FightState.*
import de.moyapro.colors.util.MenuEntryInfo
import kotlinx.coroutines.runBlocking

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "gameSaveState")


class MainActivity : ComponentActivity() {

    private val gameViewModel: GameViewModel by viewModels {
        GameViewModelFactory(this.dataStore)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainView(gameViewModel)
        }
    }

    @Composable
    private fun MainView(gameViewModel: GameViewModel) {
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

    private fun determinMenuEntries(gameState: GameState?): List<MenuEntryInfo> {
        val menuActions: MutableList<MenuEntryInfo> = mutableListOf()
        menuActions.add("Reset all progress" to ::initNewGame)
        if (gameState?.currentRun == null || gameState.currentRun.mages.isEmpty()) {
            menuActions.add("Start new Run" to { initNewGame(); startStashActivity() })
        } else {
            if (gameState.currentFight.fightState == ONGOING) {
                menuActions.add("Continue fight" to ::startFightActivity)
            } else {
                menuActions.add("Prepare next fight" to ::startStashActivity)
                menuActions.add("Loot test" to ::startLootActivity)
            }
        }
        menuActions.add("Quit" to { this.finishAffinity() })
        return menuActions
    }

    private fun initNewGame() = runBlocking {
        val initialGameState = Initializer.createInitialGameState()
        save(dataStore, initialGameState, emptyList())
        gameViewModel.reloadActions()
    }


    private fun startStashActivity() {
        this.startActivity(Intent(this, StashActivity::class.java))
    }

    private fun startLootActivity() {
        this.startActivity(Intent(this, LootActivity::class.java))
    }

    private fun startFightActivity() {
        this.startActivity(Intent(this, FightActivity::class.java))
    }


}


