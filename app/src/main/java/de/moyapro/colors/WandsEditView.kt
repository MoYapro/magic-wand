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
    val wands = currentGameState.wandsInOrder()

    Row(modifier = modifier) {
        (0..2).forEach { index ->
            if (wands.size > index) WandEditView(
                currentGameState = currentGameState,
                addAction = addAction,
                wand = wands[index]
            ) else {
                EmptyWandSlot(addAction = addAction, currentGameState = currentGameState, mageId = currentGameState.mages[index].id)
            }
        }
    }
}
