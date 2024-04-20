package de.moyapro.colors

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.takeTwo.*
import de.moyapro.colors.util.*

@Composable
fun WandEditView(
    wand: Wand = createExampleWand(),
    currentGameState: MyGameState,
    addAction: (GameAction) -> GameViewModel,
) {
    DropZone<Wand>(
        currentGameState = currentGameState,
        condition = { gameState, maybeWand -> !gameState.wands.contains(maybeWand) },
        addAction = addAction,
        onDropAction = { newWand -> buildOnDropAction(wand, newWand) }
    ) { modifier: Modifier, isInBound, dropData, hoverData ->

        val color = if (isInBound) Color.Green.copy(DROP_ZONE_ALPHA) else Color.Transparent
        Column(
            modifier = modifier
                .height(5 * SPELL_SIZE.dp)
                .width(2 * SPELL_SIZE.dp)
                .background(color)
        ) {
            val slotsByLevel =
                wand.slots.groupBy(Slot::level).toSortedMap { a, b -> b.compareTo(a) }
            slotsByLevel.forEach { (_, slots) ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    slots.forEach { slot ->
                        Draggable(
                            modifier = Modifier
                                .height(SPELL_SIZE.dp)
                                .width(SPELL_SIZE.dp),
                            dataToDrop = slot.spell,
                            onDropAction = if (null == slot.spell) null else RemoveSpellFromWandAction(wandId = wand.id, slotId = slot.id, spell = slot.spell)
                        ) {
                            SlotEditView(wand.id, slot, currentGameState, addAction)
                        }
                    }
                }
            }
        }
    }


}

private fun buildOnDropAction(currentWand: Wand, droppedWand: Wand): AddWandAction {
    check(currentWand.mageId != null) { "Current wand must always have a mage" }
    return AddWandAction(droppedWand, currentWand.mageId) { replacedWand -> AddWandToLootAction(replacedWand) }
}
