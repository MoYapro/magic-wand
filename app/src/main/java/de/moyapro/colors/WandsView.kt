package de.moyapro.colors

import android.widget.Toast
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
import de.moyapro.colors.wand.Magic
import java.util.UUID

@Composable
fun WandsView(gameViewModel: GameViewModel, mainViewModel: MainViewModel) {
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
            currentGameState.magicToPlay.forEach {
                Draggable(
                    viewModel = mainViewModel,
                    dataToDrop = createExampleMagic()) {
                    MagicView()
                }
            }
        }
        Row {
            Button(onClick = { gameViewModel.addAction(AddWandAction(createExampleWand())) }) {
                Text("moooore wands")
            }
            Button(onClick = {
                gameViewModel.addAction(
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
