package de.moyapro.colors

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import de.moyapro.colors.game.GameViewModel
import de.moyapro.colors.ui.theme.ColorsTheme

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private val gameViewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            ColorsTheme {
                MainMenu()
//                WandsView(gameViewModel, mainViewModel)
            }
        }
    }

}
