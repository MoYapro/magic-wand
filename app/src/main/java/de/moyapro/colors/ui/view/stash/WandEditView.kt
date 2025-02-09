package de.moyapro.colors.ui.view.stash

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import de.moyapro.colors.createExampleWand
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.actions.stash.PlaceSpellAction
import de.moyapro.colors.game.actions.stash.RemoveSpellFromWandAction
import de.moyapro.colors.game.model.Slot
import de.moyapro.colors.game.model.Wand
import de.moyapro.colors.game.model.gameState.GameState
import de.moyapro.colors.ui.view.dragdrop.Draggable
import de.moyapro.colors.util.SPELL_SIZE

@Composable
fun WandEditView(
    modifier: Modifier = Modifier,
    wand: Wand = createExampleWand(),
    currentGameState: GameState,
    addAction: (GameAction) -> Unit,
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
private fun SlotHelper(slot: Slot, wand: Wand, currentGameState: GameState, addAction: (GameAction) -> Unit, isWandDragged: Boolean) {
    if (null == slot.spell) {
        SlotEditView(wand.id, slot, currentGameState, addAction, isWandDragged)
    } else {
        Draggable(
            modifier = Modifier
                .height(SPELL_SIZE.dp)
                .width(SPELL_SIZE.dp),
            dataToDrop = slot.spell,
            onDropAction = RemoveSpellFromWandAction(wandId = wand.id, slotId = slot.id),
            onDropDidReplaceAction = { replacedSpell -> PlaceSpellAction(wand.id, slot.id, replacedSpell) }
        ) { _, isDragging ->
            SlotEditView(wand.id, slot, currentGameState, addAction, isDragging || isWandDragged)
        }
    }
}
