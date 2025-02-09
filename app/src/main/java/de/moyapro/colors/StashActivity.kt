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
import de.moyapro.colors.game.persistance.save

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

    private fun saveAndStartFight(currentGameState: GameState) {
        save(dataStore, currentGameState)
        startFightActivity()
    }

    private fun saveAndMain(currentGameState: GameState) {
        save(dataStore, currentGameState)
        startMainActivity()
    }

    private fun printState(currentGameState: GameState) {
        Log.d("DEBUG", currentGameState.toString())
    }

    private fun startFightActivity() {
        this.startActivity(Intent(this, FightActivity::class.java))
    }

    private fun startMainActivity() {
        this.startActivity(Intent(this, MainActivity::class.java))
    }

}
