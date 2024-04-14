package de.moyapro.colors

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.wand.*

private const val TAG = "WandsView"

@Composable
fun WandsView(currentGameState: MyGameState, addAction: (GameAction) -> GameViewModel) {

    Column {
        StatusBar(currentGameState)
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(1f / 3f)
        ) {
            items(
                items = currentGameState.enemies,
                key = { enemy: Enemy -> enemy.id.hashCode() }
            ) { enemy ->
                EnemyView(enemy, addAction)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(1f / 2f)
                .border(1.dp, Color.LightGray)
        ) {
            currentGameState.wandsInOrder().forEachIndexed { i, wand ->
                WandView(
                    modifier = Modifier.fillMaxWidth(1f / (AddWandAction.MAX_WANDS - i)),
                    wand = wand,
                    addAction = addAction,
                    currentGameState = currentGameState,
                )
            }
        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(1f / 2f)
                .border(1.dp, Color.LightGray)
        ) {
            items(
                items = currentGameState.magicToPlay,
                key = { magic: Magic -> magic.id.hashCode() }) { magic: Magic -> MagicView(magic) }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .border(1.dp, Color.LightGray)
        ) {
            Button(onClick = { addAction(AddWandAction(createExampleWand())) }) {
                Text("moooore wands")
            }
            Button(onClick = { addAction(UndoAction) }) {
                Text("undo")
            }
            Button(onClick = { addAction(EndTurnAction()) }) {
                Text("End Turn")
            }
        }
    }
}
