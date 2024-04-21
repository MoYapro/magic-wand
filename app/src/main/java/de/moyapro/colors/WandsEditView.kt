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

    Row(modifier = modifier) {
        currentGameState.mages.forEach { mage ->
            val wand = currentGameState.findWand(mage.id)
            if (wand != null) {
                Draggable(
                    dataToDrop = wand,
                    requireLongPress = true,
                    onDropAction = RemoveWandAction(wand),
                    onDropDidReplaceAction = { replacedWand -> AddWandAction(replacedWand, mage.id) }
                ) { theWand ->
                    WandEditView(
                        currentGameState = currentGameState,
                        addAction = addAction,
                        wand = theWand
                    )
                }
            } else {
                EmptyWandSlot(addAction = addAction, currentGameState = currentGameState, mageId = mage.id)
            }
        }
    }
}
