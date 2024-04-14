package de.moyapro.colors

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.*

@Composable
fun WandsEditView(
    modifier: Modifier = Modifier,
    currentGameState: MyGameState,
    addAction: (GameAction) -> GameViewModel,
) {
    val wands = currentGameState.wands

    Row(modifier = modifier) {

        if (wands.size > 0) WandEditView(
            modifier = Modifier
                .fillMaxWidth(1f / 3f)
                .fillMaxHeight(),
            currentGameState = currentGameState,
            addAction = addAction,
            wand = wands[0]
        ) else {
            EmptyWandSlot(addAction = addAction, currentGameState = currentGameState)
        }
        if (wands.size > 1) WandEditView(
            modifier = Modifier.fillMaxWidth(1f / 2f),
            currentGameState = currentGameState,
            addAction = addAction,
            wand = wands[1]
        ) else {
            EmptyWandSlot(addAction = addAction, currentGameState = currentGameState)
        }
        if (wands.size > 2) WandEditView(
            modifier = Modifier.fillMaxWidth(1f),
            currentGameState = currentGameState,
            addAction = addAction,
            wand = wands[2]
        ) else {
            EmptyWandSlot(addAction = addAction, currentGameState = currentGameState)
        }
    }
}
