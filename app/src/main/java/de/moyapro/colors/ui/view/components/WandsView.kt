package de.moyapro.colors.ui.view.components

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
import de.moyapro.colors.game.actions.fight.*
import de.moyapro.colors.game.enemy.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.ui.view.fight.*

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
                key = { enemy: Enemy -> enemy.hashCode() }
            ) { enemy ->
                EnemyView(enemy, addAction)
            }
        }

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(1f / 2f)
                .border(1.dp, Color.LightGray)
        ) {
            items(
                items = currentGameState.wandsInOrder(),
                key = { wand: Wand -> wand.hashCode() }
            ) { theWand ->
                WandView(
                    wand = theWand,
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
                key = { magic: Magic -> magic.hashCode() }) { magic: Magic -> MagicView(magic) }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .border(1.dp, Color.LightGray)
        ) {
            Button(onClick = { addAction(UndoAction) }) {
                Text("undo")
            }
            Button(onClick = { addAction(EndTurnAction()) }) {
                Text("End Turn")
            }
        }
    }
}
