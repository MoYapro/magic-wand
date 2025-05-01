package de.moyapro.colors

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import de.moyapro.colors.game.GameViewModel
import de.moyapro.colors.game.GameViewModelFactory
import de.moyapro.colors.game.generators.Initializer
import de.moyapro.colors.game.model.gameState.GameState

class StashActivity : ComponentActivity() {

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
            StashView(
                currentGameState,
                ::startFightActivity,
                ::startMainActivity,
                ::printState,
                gameViewModel::addAction,
                gameViewModel::materializeActions,
                gameViewModel::reloadState
            )
        }
    }

    private fun printState(currentGameState: GameState) {
        Log.d("DEBUG", currentGameState.toString())
    }

    private fun startFightActivity() {
        gameViewModel.materializeActions()
        this.startActivity(Intent(this, FightActivity::class.java))
    }

    private fun startMainActivity() {
        gameViewModel.materializeActions()
        this.startActivity(Intent(this, MainActivity::class.java))
    }

}
