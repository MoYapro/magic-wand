package de.moyapro.colors.ui.view.loot

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.actions.loot.AddWandAction
import de.moyapro.colors.game.actions.loot.RemoveWandAction
import de.moyapro.colors.game.model.Mage
import de.moyapro.colors.game.model.Wand
import de.moyapro.colors.game.model.accessor.findWandOnMage
import de.moyapro.colors.game.model.gameState.GameState
import de.moyapro.colors.ui.view.components.EmptyWandSlot
import de.moyapro.colors.ui.view.dragdrop.Draggable
import de.moyapro.colors.ui.view.dragdrop.DropZone

@Composable
fun WandsEditView(
    modifier: Modifier = Modifier,
    currentGameState: GameState,
    addAction: (GameAction) -> Unit,
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
    currentGameState: GameState,
    addAction: (GameAction) -> Unit,
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
