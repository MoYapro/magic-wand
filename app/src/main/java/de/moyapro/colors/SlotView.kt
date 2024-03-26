package de.moyapro.colors

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.moyapro.colors.game.GameViewModel
import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.actions.PlaceMagicAction
import de.moyapro.colors.takeTwo.Slot
import de.moyapro.colors.takeTwo.WandId
import de.moyapro.colors.util.DROP_ZONE_ALPHA
import de.moyapro.colors.util.SPELL_SIZE
import de.moyapro.colors.wand.Magic
import de.moyapro.colors.wand.MagicSlot

@Composable
fun SlotView(
    wandId: WandId,
    slot: Slot = createExampleSlot(),
    addAction: (GameAction) -> GameViewModel,
    currentGameState: MyGameState,
) {
    DropZone<Magic>(
        modifier = Modifier
            .border(BorderStroke(1.dp, Color.LightGray))
            .size(SPELL_SIZE.dp),
        currentGameState = currentGameState,
    ) { isInBound: Boolean, droppedMagic: Magic?, hoveredMagic: Magic? ->
        val isThisSlotFull = slot.magicSlots.none { it.placedMagic == null }
        val hoveredMagicDoesFit =
            !isThisSlotFull && slot.magicSlots.any { it.requiredMagic.type == hoveredMagic?.type && it.placedMagic == null }
        if (droppedMagic != null && hoveredMagicDoesFit) {
            LaunchedEffect(key1 = droppedMagic) {
                addAction(PlaceMagicAction(wandId, slot.id, droppedMagic))
            }
        }
        val usedColor: Color = when {
            !isInBound -> Color.Transparent
            isInBound && !hoveredMagicDoesFit -> Color.Red.copy(alpha = DROP_ZONE_ALPHA)
            isInBound && hoveredMagicDoesFit -> Color.Green.copy(alpha = DROP_ZONE_ALPHA)
            else -> throw IllegalStateException("cannot determine hover color")
        }

        Box(modifier = Modifier
            .background(usedColor)
            .fillMaxSize()) {
            Row {
                Text("".padStart(slot.power, '|'))
                Text(slot.spell?.name ?: "empty")
                LazyRow {
                    items(
                        items = slot.magicSlots,
                        key = { magicSlot: MagicSlot -> magicSlot.id.hashCode() }) { magicSlot: MagicSlot ->
                        MagicSlotView(magicSlot)
                    }
                }

            }
        }
    }
}
