package de.moyapro.colors

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import de.moyapro.colors.game.GameViewModel
import de.moyapro.colors.game.GameViewModelFactory
import de.moyapro.colors.game.model.Bonk
import de.moyapro.colors.game.model.Fizz
import de.moyapro.colors.game.model.Spell
import de.moyapro.colors.game.model.Splash
import de.moyapro.colors.game.model.Wand
import de.moyapro.colors.game.model.gameState.GameState
import de.moyapro.colors.ui.view.loot.LootView

class LootActivity : ComponentActivity() {

    private val gameViewModel: GameViewModel by viewModels {
        GameViewModelFactory(this.dataStore)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val newWands: List<Wand> = listOf(createExampleWand(null), createExampleWand(null))
        val newSpells: List<Spell<*>> = listOf(Fizz(), Bonk(), Splash())
        setContent {
            val currentGameStateResult: Result<GameState> by gameViewModel.uiState.collectAsState()
            LootView(
                newSpells = newSpells,
                newWands = newWands,
                currentGameState = currentGameStateResult.getOrThrow(),
                addAction = gameViewModel::addAction,
                goToNextScreenAction = ::saveAndStartStashActivity
            )
        }
    }

    private fun saveAndStartStashActivity() {
        gameViewModel.materializeActions()
        startStashActivity()
    }

    private fun saveAndMain() {
        gameViewModel.materializeActions()
        startMainActivity()
    }

    private fun startStashActivity() {
        gameViewModel.materializeActions()
        this.startActivity(Intent(this, StashActivity::class.java))
    }

    private fun startMainActivity() {
        gameViewModel.materializeActions()
        this.startActivity(Intent(this, MainActivity::class.java))
    }

}
