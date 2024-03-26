package de.moyapro.colors

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import de.moyapro.colors.game.GameViewModel
import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.ui.theme.ColorsTheme

class FightActivity : ComponentActivity() {

    private val gameViewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val currentGameStateResult: Result<MyGameState> by gameViewModel.uiState.collectAsState()

            val currentGameState: MyGameState = currentGameStateResult.getOrElse {
                Toast.makeText(LocalContext.current, it.message, Toast.LENGTH_LONG).show()
                MyGameState(emptyList(), emptyList(), emptyList(), 0, emptyList())
            }
            ColorsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    WandsView(currentGameState, gameViewModel::addAction)
                }
            }
        }
    }
}