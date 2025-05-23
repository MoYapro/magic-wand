package de.moyapro.colors

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import de.moyapro.colors.game.GameViewModel
import de.moyapro.colors.game.GameViewModelFactory
import de.moyapro.colors.game.actions.fight.EndFightAction
import de.moyapro.colors.game.actions.fight.StartFightAction
import de.moyapro.colors.game.generators.Initializer
import de.moyapro.colors.game.model.gameState.GameState
import de.moyapro.colors.ui.theme.ColorsTheme
import de.moyapro.colors.ui.view.components.WandsView
import de.moyapro.colors.ui.view.fight.LostFightView
import de.moyapro.colors.ui.view.fight.WinFightView
import de.moyapro.colors.util.FightState.*

private const val TAG = "FightActivity"

class FightActivity : ComponentActivity() {

    private val gameViewModel: GameViewModel by viewModels {
        GameViewModelFactory(this.dataStore)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val currentGameStateResult: Result<GameState> by gameViewModel.uiState.collectAsState()

            val currentGameState: GameState = currentGameStateResult.getOrElse {
                Toast.makeText(LocalContext.current, it.message, Toast.LENGTH_LONG).show()
                Initializer.createInitialGameState()
            }
            ColorsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = Color.Gray
                ) {
                    when (currentGameState.currentFight.fightState) {
                        NOT_STARTED -> {
                            Log.d(TAG, "Fight not started yet")
                            gameViewModel.materializeActions()
                            gameViewModel.addAction(StartFightAction())
                        }

                        ONGOING -> WandsView(currentGameState, gameViewModel::addAction)
                        WIN -> WinFightView(::startLootActivity)
                        LOST -> LostFightView(::startMainActivity)
                    }
                }
            }
        }
    }

    private fun startMainActivity() {
        gameViewModel.addAction(EndFightAction())
        gameViewModel.materializeActions()
        this.startActivity(Intent(this, MainActivity::class.java))
    }

    private fun startLootActivity() {
        gameViewModel.addAction(EndFightAction())
        gameViewModel.materializeActions()
        this.startActivity(Intent(this, LootActivity::class.java))
    }
}
