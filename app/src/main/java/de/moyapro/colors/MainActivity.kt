package de.moyapro.colors

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import de.moyapro.colors.game.GameViewModel
import de.moyapro.colors.ui.theme.ColorsTheme

class MainActivity : ComponentActivity() {

    private val mainViewModel = MainViewModel()
    private val gameViewModel = GameViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            ColorsTheme {
                WandsView(gameViewModel, mainViewModel)
//                DragableScreen(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(MaterialTheme.colorScheme.background)
//                ) {
//                    MainScreen(viewModel)
//                }
            }
        }
    }

}
