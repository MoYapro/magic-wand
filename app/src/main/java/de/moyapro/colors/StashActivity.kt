package de.moyapro.colors

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import de.moyapro.colors.game.GameViewModel
import de.moyapro.colors.game.GameViewModelFactory
import de.moyapro.colors.game.model.gameState.GameState

class StashActivity : ComponentActivity() {

    private val gameViewModel: GameViewModel by viewModels {
        GameViewModelFactory(this.dataStore)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StashView(gameViewModel, ::saveAndStartFight, ::saveAndMain, ::printState)
        }
    }

    private fun saveAndStartFight() {
        gameViewModel.materializeActions()
        startFightActivity()
    }

    private fun saveAndMain() {
        gameViewModel.materializeActions()
        startMainActivity()
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
