package de.moyapro.colors

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import de.moyapro.colors.game.AddWandAction
import de.moyapro.colors.game.GameViewModel
import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.game.PlaceMagicAction
import de.moyapro.colors.takeTwo.SlotId
import de.moyapro.colors.takeTwo.WandId
import de.moyapro.colors.ui.theme.ColorsTheme
import de.moyapro.colors.wand.Magic
import java.util.UUID

class MainActivity : ComponentActivity() {

    private val viewModel = MainViewModel()
    private val gameViewModel = GameViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            ColorsTheme {
                WandsView(gameViewModel)
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

    @Composable
    fun WandsView(viewModel: GameViewModel) {
        val currentGameStateResult: Result<MyGameState> by gameViewModel.uiState.collectAsState()

        val currentGameState: MyGameState = currentGameStateResult.getOrElse {
            Toast.makeText(LocalContext.current, it.message, Toast.LENGTH_LONG).show()
            MyGameState(emptyList(), emptyList())
        }
        Column {
            Text("All Wands:")
            Row {
                currentGameState.wands.forEach { WandView(it) }
            }
            Row {
                currentGameState.magicToPlay.forEach { MagicView(createExampleMagic()) }
            }
            Row {
                Button(onClick = { viewModel.addAction(AddWandAction(createExampleWand())) }) {
                    Text("moooore wands")
                }
                Button(onClick = {
                    viewModel.addAction(
                        PlaceMagicAction(
                            WandId(UUID.randomUUID()),
                            SlotId(UUID.randomUUID()),
                            Magic()
                        )
                    )
                }) {
                    Text("error")
                }
            }
        }
    }
}
