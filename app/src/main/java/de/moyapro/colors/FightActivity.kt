package de.moyapro.colors

import android.content.*
import android.os.*
import android.widget.*
import androidx.activity.*
import androidx.activity.compose.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.*
import de.moyapro.colors.game.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.ui.theme.*
import de.moyapro.colors.ui.view.components.*
import de.moyapro.colors.ui.view.fight.*
import de.moyapro.colors.util.*


class FightActivity : ComponentActivity() {

    private val gameViewModel: GameViewModel by viewModels {
        GameViewModelFactory(this.dataStore)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val currentGameStateResult: Result<MyGameState> by gameViewModel.uiState.collectAsState()
            val currentGameState: MyGameState = currentGameStateResult.getOrElse {
                Toast.makeText(LocalContext.current, it.message, Toast.LENGTH_LONG).show()
                MyGameState(emptyList(), emptyList(), emptyList(), 0, emptyList())
            }

            if (currentGameState.fightHasEnded == FightOutcome.WIN) {
                //    initNextBattle(roundNumber = 1)
            }

            ColorsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Gray
                ) {
                    when (currentGameState.fightHasEnded) {
                        FightOutcome.ONGOING -> WandsView(
                            currentGameState,
                            gameViewModel::addAction
                        )

                        FightOutcome.WIN -> WinFightView(::startMainActivity)
                        FightOutcome.LOST -> LostFightView(::startMainActivity)
                    }
                }
            }
        }
    }

    private fun initNextBattle(gameViewModel: GameViewModel, roundNumber: Int) {
//        gameViewModel.materializeActions()
        // place enemies based on roundNumber
    }

    private fun startMainActivity() {
        this.startActivity(Intent(this, MainActivity::class.java))
    }
}
