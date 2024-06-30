package de.moyapro.colors.ui.view.loot

import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.actions.loot.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.accessor.*
import de.moyapro.colors.game.model.gameState.*
import de.moyapro.colors.ui.view.components.*
import de.moyapro.colors.ui.view.dragdrop.*

@Composable
fun WandsEditView(
    modifier: Modifier = Modifier,
    currentGameState: NewGameState,
    addAction: (GameAction) -> GameViewModel,
) {
    LazyRow(modifier = modifier) {
        items(items = currentGameState.currentRun.mages, key = { mage: Mage -> mage.hashCode() }) { mage: Mage ->
            val wand = currentGameState.currentRun.activeWands.findWandOnMage(mage.id)
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
    currentGameState: NewGameState,
    addAction: (GameAction) -> GameViewModel,
    mage: Mage,
) {
    require(wand.mageId != null) { "There is a wand without a mage in Wands edit view" }
    require(wand.mageId == mage.id) { "Mage and Wand.mageId do not match up" }
    DropZone<Wand>(
        currentGameState = currentGameState,
        condition = { _, newWand -> newWand.mageId != mage.id && newWand.id != wand.id },
        addAction = addAction,
        onDropAction = { newWand -> AddWandAction(newWand, wand.mageId) },
        emitData = wand,
    ) { modifier: Modifier, _, _ ->
        Draggable(dataToDrop = wand,
            requireLongPress = true,
            onDropAction = RemoveWandAction(wand),
            onDropDidReplaceAction = { replacedWand -> AddWandAction(replacedWand, mage.id) }) { theWand, isDragging ->
            WandEditView(
                modifier, wand = theWand, currentGameState = currentGameState, addAction = addAction, isWandDragged = isDragging
            )
        }
    }
}
