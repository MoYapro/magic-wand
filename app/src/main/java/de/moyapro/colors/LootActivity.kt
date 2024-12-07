package de.moyapro.colors

import android.content.*
import android.os.*
import android.util.*
import android.widget.*
import androidx.activity.*
import androidx.activity.compose.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.unit.*
import de.moyapro.colors.game.*
import de.moyapro.colors.game.generators.*
import de.moyapro.colors.game.model.gameState.*
import de.moyapro.colors.game.persistance.*
import de.moyapro.colors.ui.theme.*
import de.moyapro.colors.ui.view.loot.*
import de.moyapro.colors.util.*

class LootActivity : ComponentActivity() {

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
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Gray
                ) {
                    Column(Modifier.fillMaxSize()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(3 * SPELL_SIZE.dp)
                                .border(1.dp, Color.LightGray)
                        ) {
                            LootWandsView(
                                currentGameState = currentGameState,
                                addAction = gameViewModel::addAction
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2 * SPELL_SIZE.dp)
                                .border(1.dp, Color.LightGray)
                        ) {
                            LootSpellsView(currentGameState = currentGameState, addAction = gameViewModel::addAction)
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(3 * SPELL_SIZE.dp)
                                .border(1.dp, Color.LightGray)
                        ) {
                            WandsEditView(
                                currentGameState = currentGameState,
                                addAction = gameViewModel::addAction
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(1f / 4f)
                                .border(1.dp, Color.LightGray)
                        ) {
                            Button(onClick = { saveAndStartFight(currentGameState) }) {
                                Text(text = "Next fight")
                            }
                            Button(onClick = { saveAndMain(currentGameState) }) {
                                Text(text = "Main menu")
                            }
                            Button(onClick = { printState(currentGameState) }) {
                                Text(text = "Debug state")
                            }
                        }
                    }
                }
            }
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
