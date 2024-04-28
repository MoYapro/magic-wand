package de.moyapro.colors

import androidx.compose.foundation.lazy.*
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
    LazyRow(modifier = modifier) {
        items(
            items = currentGameState.mages,
            key = { mage: Mage -> mage.hashCode() }) { mage: Mage ->
            val wand = currentGameState.findWand(mage.id)
            if (null == wand) {
                EmptyWandSlot(addAction = addAction, currentGameState = currentGameState, mageId = mage.id)
            } else {
                ShowEditView(wand, currentGameState, addAction, mage)
            }
        }
    }
}

@Composable
private fun ShowEditView(
    wand: Wand,
    currentGameState: MyGameState,
    addAction: (GameAction) -> GameViewModel,
    mage: Mage,
) {
    require(wand.mageId != null) { "There is a wand without a mage in Wands edit view" }
    require(wand.mageId == mage.id) { "Mage and Wand.mageId to not match up" }
    DropZone<Wand>(
        currentGameState = currentGameState,
        condition = { gameState, newWand -> !gameState.wands.contains(newWand) },
        addAction = addAction,
        onDropAction = { newWand ->
            AddWandAction(newWand, wand.mageId)
        },
        emitData = wand,
    ) { modifier: Modifier, _, _ ->
        Draggable(
            dataToDrop = wand,
            requireLongPress = true,
            onDropAction = RemoveWandAction(wand),
            onDropDidReplaceAction = { replacedWand -> AddWandAction(replacedWand, mage.id) }
        ) { theWand, isDragging ->
            WandEditView(
                modifier,
                wand = theWand,
                currentGameState = currentGameState,
                addAction = addAction,
                isWandDragged = isDragging
            )
        }
    }
}
