package de.moyapro.colors

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import de.moyapro.colors.game.GameViewModel
import de.moyapro.colors.game.GameViewModelFactory
import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.ui.theme.ColorsTheme

class LootActivity : ComponentActivity() {

    private val gameViewModel: GameViewModel by viewModels {
        GameViewModelFactory(this.dataStore)
    }
    private val mainViewModel: MainViewModel by viewModels()

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
                                .fillMaxHeight(1f / 4f)
                                .border(1.dp, Color.LightGray)
                        ) {
                            LootWandsView(
                                wands = currentGameState.loot.wands,
                                mainViewModel = mainViewModel,
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
                            Text(text = "new Spells")
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(1f / 4f)
                                .border(1.dp, Color.LightGray)
                        ) {
                            WandsEditView(
                                currentGameState = currentGameState,
                                mainViewModel = mainViewModel,
                                addAction = gameViewModel::addAction
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(1f / 3f)
                                .border(1.dp, Color.LightGray)
                        ) {
                            Button(onClick = ::startMainActivity) {
                                Text(text = "Done")
                            }
                        }
                    }
                }
            }
        }
    }

    private fun startMainActivity() {
        this.startActivity(Intent(this, MainActivity::class.java))
    }
}
