package de.moyapro.colors.ui.view.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.actions.fight.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.accessor.*
import de.moyapro.colors.game.model.gameState.*
import de.moyapro.colors.ui.view.fight.*

private const val TAG = "WandsView"

@Composable
fun WandsView(currentGameState: GameState, addAction: (GameAction) -> Unit) {

    Column {
        StatusBar(currentGameState)
        BattleBoardView(
            battleBoard = currentGameState.currentFight.battleBoard,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(1f / 3f),
            addAction,
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(1f / 2f)
                .border(1.dp, Color.LightGray)
        ) {
            items(
                items = currentGameState.currentFight.wands.inOrder(),
                key = { wand: Wand -> wand.id.hashCode() }
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
                items = currentGameState.currentFight.magicToPlay,
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
            Button(onClick = { addAction(LoseFightAction()) }) {
                Text("Give up")
            }
            Button(onClick = { addAction(WinFightAction()) }) {
                Text("Win fight")
            }
        }
    }
}
