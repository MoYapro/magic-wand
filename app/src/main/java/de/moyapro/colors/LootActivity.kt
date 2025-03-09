package de.moyapro.colors

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import de.moyapro.colors.game.GameViewModel
import de.moyapro.colors.game.GameViewModelFactory
import de.moyapro.colors.game.model.Bonk
import de.moyapro.colors.game.model.Fizz
import de.moyapro.colors.game.model.Spell
import de.moyapro.colors.game.model.Splash
import de.moyapro.colors.game.model.Wand
import de.moyapro.colors.game.model.gameState.GameState
import de.moyapro.colors.game.persistance.save
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
            LootView(newSpells = newSpells, newWands = newWands, addAction = gameViewModel::addAction)
        }
    }

    private fun saveAndStartStashActivity(currentGameState: GameState) {
        save(dataStore, currentGameState)
        startStashActivity()
    }

    private fun saveAndMain(currentGameState: GameState) {
        save(dataStore, currentGameState)
        startMainActivity()
    }

    private fun startStashActivity() {
        this.startActivity(Intent(this, StashActivity::class.java))
    }

    private fun startMainActivity() {
        this.startActivity(Intent(this, MainActivity::class.java))
    }

}
