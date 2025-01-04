package de.moyapro.colors.ui.view.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.actions.UndoAction
import de.moyapro.colors.game.actions.fight.EndTurnAction
import de.moyapro.colors.game.actions.fight.LoseFightAction
import de.moyapro.colors.game.actions.fight.WinFightAction
import de.moyapro.colors.game.model.Magic
import de.moyapro.colors.game.model.Wand
import de.moyapro.colors.game.model.accessor.inOrder
import de.moyapro.colors.game.model.gameState.GameState
import de.moyapro.colors.ui.view.fight.StatusBar

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
