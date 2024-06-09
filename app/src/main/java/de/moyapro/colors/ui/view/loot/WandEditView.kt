package de.moyapro.colors.ui.view.loot

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import de.moyapro.colors.*
import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.actions.loot.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.ui.view.dragdrop.*
import de.moyapro.colors.util.*

@Composable
fun WandEditView(
    modifier: Modifier = Modifier,
    wand: Wand = createExampleWand(),
    currentGameState: MyGameState,
    addAction: (GameAction) -> GameViewModel,
    isWandDragged: Boolean = false,
) {
    val slotsByLevel = wand.slots.groupBy(Slot::level).toSortedMap { key1, key2 -> key2.compareTo(key1) }
    val maxLevel = slotsByLevel.keys.max()

    Column(
        modifier = modifier
            .height(5 * SPELL_SIZE.dp)
            .width(2 * SPELL_SIZE.dp)
    ) {
        (maxLevel downTo 0).forEach { level ->
            LazyRow(modifier = modifier.align(Alignment.CenterHorizontally)) {
                items(slotsByLevel[level]!!, key = { slot -> slot.hashCode() })
                { slot -> SlotHelper(slot, wand, currentGameState, addAction, isWandDragged) }
            }
        }
    }
}

@Composable
private fun SlotHelper(slot: Slot, wand: Wand, currentGameState: MyGameState, addAction: (GameAction) -> GameViewModel, isWandDragged: Boolean) {
    if (null == slot.spell) {
        SlotEditView(wand.id, slot, currentGameState, addAction, isWandDragged)
    } else {
        Draggable(
            modifier = Modifier
                .height(SPELL_SIZE.dp)
                .width(SPELL_SIZE.dp),
            dataToDrop = slot.spell,
            onDropAction = RemoveSpellFromWandAction(wandId = wand.id, slotId = slot.id, spell = slot.spell),
            onDropDidReplaceAction = { replacedSpell -> PlaceSpellAction(wand.id, slot.id, replacedSpell) }
        ) { _, isDragging ->
            SlotEditView(wand.id, slot, currentGameState, addAction, isDragging || isWandDragged)
        }
    }
}
