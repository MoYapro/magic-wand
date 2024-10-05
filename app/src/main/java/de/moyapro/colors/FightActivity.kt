package de.moyapro.colors

import android.content.*
import android.os.*
import androidx.activity.*
import androidx.activity.compose.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.fight.*
import de.moyapro.colors.game.model.gameState.*
import de.moyapro.colors.ui.theme.*
import de.moyapro.colors.ui.view.components.*
import de.moyapro.colors.ui.view.fight.*
import de.moyapro.colors.util.FightState.LOST
import de.moyapro.colors.util.FightState.NOT_STARTED
import de.moyapro.colors.util.FightState.ONGOING
import de.moyapro.colors.util.FightState.WIN


class FightActivity : ComponentActivity() {

    private val gameViewModel: GameViewModel by viewModels {
        GameViewModelFactory(this.dataStore)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val currentGameStateResult: Result<GameState> by gameViewModel.uiState.collectAsState()
            val currentGameState: GameState = currentGameStateResult.getOrThrow()

            ColorsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Gray
                ) {
                    when (currentGameState.currentFight.fightState) {
                        NOT_STARTED -> gameViewModel.addAction(StartFightAction())
                        ONGOING -> WandsView(
                            currentGameState,
                            gameViewModel::addAction
                        )

                        WIN -> WinFightView(::startLootActivity)
                        LOST -> LostFightView(::startMainActivity)
                    }
                }
            }
        }
    }

    private fun startMainActivity() {
        gameViewModel.addAction(EndFightAction())
        this.startActivity(Intent(this, MainActivity::class.java))
    }
    private fun startLootActivity() {
        gameViewModel.addAction(EndFightAction())
        this.startActivity(Intent(this, LootActivity::class.java))
    }
}
