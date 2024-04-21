package de.moyapro.colors

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.takeTwo.*

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
                require(wand.mageId != null) { "There is a wand without a mage in Wands edit view" }
                DropZone<Wand>(
                    currentGameState = currentGameState,
                    condition = { gameState, maybeWand -> !gameState.wands.contains(maybeWand) },
                    addAction = addAction,
                    onDropAction = { newWand -> AddWandAction(newWand, wand.mageId) },
                    emitData = wand,
                ) { modifier: Modifier, isInBound, dropData, hoverData ->
                Draggable(
                    dataToDrop = wand,
                    requireLongPress = true,
                    onDropAction = RemoveWandAction(wand),
                    onDropDidReplaceAction = { replacedWand -> AddWandAction(replacedWand, mage.id) }
                ) { theWand ->
                    WandEditView(
                        modifier,
                        currentGameState = currentGameState,
                        addAction = addAction,
                        wand = theWand
                    )
                }
                }
            } else {
                EmptyWandSlot(addAction = addAction, currentGameState = currentGameState, mageId = mage.id)
            }
        }
    }
}
