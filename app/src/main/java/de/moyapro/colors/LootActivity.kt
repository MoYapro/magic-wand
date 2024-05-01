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
import androidx.datastore.preferences.core.*
import de.moyapro.colors.game.*
import de.moyapro.colors.ui.theme.*
import de.moyapro.colors.util.*
import kotlinx.coroutines.*

class LootActivity : ComponentActivity() {

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
            ColorsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    Column(Modifier.fillMaxSize()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(3 * SPELL_SIZE.dp)
                                .border(1.dp, Color.LightGray)
                        ) {
                            LootWandsView(
                                wands = currentGameState.loot.wands,
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
                            LootSpellsView(spells = currentGameState.loot.spells, currentGameState = currentGameState, addAction = gameViewModel::addAction)
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
                            Button(onClick = ::startMainActivity) {
                                Text(text = "Done")
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

    private fun printState(currentGameState: MyGameState) {
        Log.d("DEBUG", currentGameState.toString())
    }

    private fun startMainActivity() {
        save(this.gameViewModel.getCurrentGameState().getOrThrow())
        this.startActivity(Intent(this, MainActivity::class.java))
    }

    fun save(gameState: MyGameState): Unit = runBlocking {
        dataStore.edit { settings ->
            settings[WAND_STATE] = getConfiguredJson().writeValueAsString(gameState.wands)
        }
    }
}
