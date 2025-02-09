package de.moyapro.colors.ui.view.stash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.actions.stash.AddWandToStashAction
import de.moyapro.colors.game.actions.stash.RemoveWandFromStashAction
import de.moyapro.colors.game.model.Wand
import de.moyapro.colors.game.model.gameState.GameState
import de.moyapro.colors.ui.view.dragdrop.Draggable
import de.moyapro.colors.ui.view.dragdrop.DropZone
import de.moyapro.colors.util.SPELL_SIZE


@Composable
fun StashWandsView(
    currentGameState: GameState,
    addAction: (GameAction) -> Unit,
) {
    DropZone<Wand>(
        addAction = addAction,
        currentGameState = currentGameState,
        condition = { gameState: GameState, dropData: Wand -> !gameState.currentRun.wandsInBag.contains(dropData) },
        onDropAction = { droppedWand -> AddWandToStashAction(droppedWand) },
    ) { modifier: Modifier, _: Boolean, _: Wand? ->
        val wands = currentGameState.currentRun.wandsInBag
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(3 * SPELL_SIZE.dp)
        ) {
            if (wands.isEmpty()) {
                Text("Win fights to get wands")
            } else {
                LazyRow(
                    modifier = modifier.fillMaxSize()
                ) {
                    items(
                        items = wands,
                        key = { wand -> wand.hashCode() })
                    { wand ->
                        require(wand.mageId == null) { "Wand in stash must not have a mage" }
                        Draggable(
                            dataToDrop = wand,
                            onDropAction = RemoveWandFromStashAction(wand),
                            requireLongPress = true,
                            onDropDidReplaceAction = { replacedWand -> AddWandToStashAction(replacedWand) }
                        ) { theWand, isDragging ->
                            WandEditView(
                                wand = theWand,
                                addAction = addAction,
                                currentGameState = currentGameState,
                                isWandDragged = isDragging
                            )
                        }
                    }
                }
            }
        }
    }
}
