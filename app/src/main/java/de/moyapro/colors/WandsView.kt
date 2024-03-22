package de.moyapro.colors

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import de.moyapro.colors.game.Enemy
import de.moyapro.colors.game.EnemyView
import de.moyapro.colors.game.GameViewModel
import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.game.actions.AddWandAction
import de.moyapro.colors.game.actions.EndTurnAction
import de.moyapro.colors.wand.Magic

private const val TAG = "WandsView"

@Composable
fun WandsView(gameViewModel: GameViewModel, mainViewModel: MainViewModel) {
    val currentGameStateResult: Result<MyGameState> by gameViewModel.uiState.collectAsState()

    val currentGameState: MyGameState = currentGameStateResult.getOrElse {
        Toast.makeText(LocalContext.current, it.message, Toast.LENGTH_LONG).show()
        MyGameState(emptyList(), emptyList(), emptyList(), 0, emptyList())
    }
    Column {
        StatusBar(currentGameState)
        LazyRow {
            items(
                items = currentGameState.enemies,
                key = { enemy: Enemy -> enemy.id.hashCode() }
            ) { enemy ->
                EnemyView(enemy, gameViewModel)
            }
        }

        Text("All Wands:")
        Row {
            currentGameState.wands.forEach { WandView(it, gameViewModel) }
        }
        LazyRow {
            items(
                items = currentGameState.magicToPlay,
                key = { magic: Magic -> magic.id.hashCode() }) { magic: Magic -> MagicView(magic) }
        }

        Row {
            Button(onClick = { gameViewModel.addAction(AddWandAction(createExampleWand())) }) {
                Text("moooore wands")
            }
            Button(onClick = gameViewModel::undoLastAction) {
                Text("undo")
            }
            Button(onClick = { gameViewModel.addAction(EndTurnAction()) }) {
                Text("End Turn")
            }
        }
    }
}
