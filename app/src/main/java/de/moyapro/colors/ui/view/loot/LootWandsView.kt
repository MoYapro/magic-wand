package de.moyapro.colors.ui.view.loot

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.actions.loot.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.ui.view.dragdrop.*
import de.moyapro.colors.util.*


@Composable
fun LootWandsView(
    wands: List<Wand>,
    currentGameState: MyGameState,
    addAction: (GameAction) -> GameViewModel,
) {
    DropZone<Wand>(
        addAction = addAction,
        currentGameState = currentGameState,
        condition = { gameState: MyGameState, dropData: Wand -> !gameState.loot.wands.contains(dropData) },
        onDropAction = { droppedWand -> AddWandToLootAction(droppedWand) },
    ) { modifier: Modifier, isInBound: Boolean, hoveredWand: Wand? ->
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(3 * SPELL_SIZE.dp)
        ) {
            if (wands.size >= 1) {
                LazyRow(
                    modifier = modifier.fillMaxSize()
                ) {
                    items(
                        items = wands,
                        key = { wand -> wand.hashCode() })
                    { wand ->
                        require(wand.mageId == null) { "Wand in loot must not have a mage" }
                        Draggable(
                            dataToDrop = wand,
                            onDropAction = RemoveWandFromLootAction(wand),
                            requireLongPress = true,
                            onDropDidReplaceAction = { replacedWand -> AddWandToLootAction(replacedWand) }
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
            } else {
                Text("Win fights to get wands")
            }
        }
    }
}
