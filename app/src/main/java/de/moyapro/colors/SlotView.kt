package de.moyapro.colors

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.takeTwo.*
import de.moyapro.colors.util.*
import de.moyapro.colors.wand.*

@Composable
fun SlotView(
    wandId: WandId,
    slot: Slot = createExampleSlot(),
    addAction: (GameAction) -> GameViewModel,
    currentGameState: MyGameState,
) {
    DropZone<Any>(
        modifier = Modifier
            .border(BorderStroke(1.dp, Color.LightGray))
            .size(SPELL_SIZE.dp),
        currentGameState = currentGameState,
        addAction = addAction,
    ) { modifier: Modifier, isInBound: Boolean, droppedMagic: Any?, hoveredMagic: Any? ->
        val useDroppedMagic: Magic? = castOrNull(droppedMagic)
        val useHoveredMagic: Magic? = castOrNull(hoveredMagic)

        val isThisSlotFull = slot.spell?.magicSlots?.none { it.placedMagic == null } ?: true
        val hoveredMagicDoesFit =
            !isThisSlotFull && slot.spell?.magicSlots?.any { it.requiredMagic.type == useHoveredMagic?.type && it.placedMagic == null } ?: false
        if (useDroppedMagic != null && hoveredMagicDoesFit) {
            LaunchedEffect(key1 = useDroppedMagic) {
                addAction(PlaceMagicAction(wandId, slot.id, useDroppedMagic))
            }
        }
        val usedColor: Color = when {
            !isInBound -> Color.Transparent
            isInBound && !hoveredMagicDoesFit -> Color.Red.copy(alpha = DROP_ZONE_ALPHA)
            isInBound && hoveredMagicDoesFit -> Color.Green.copy(alpha = DROP_ZONE_ALPHA)
            else -> throw IllegalStateException("cannot determine hover color")
        }

        Box(
            modifier = Modifier
                .background(usedColor)
                .fillMaxSize()
        ) {
            Row {
                Text("".padStart(slot.power, '|'))
                Text(slot.spell?.name ?: "empty")
                LazyRow {
                    items(
                        items = slot.spell?.magicSlots ?: emptyList(),
                        key = { magicSlot: MagicSlot -> magicSlot.id.hashCode() }) { magicSlot: MagicSlot ->
                        MagicSlotView(magicSlot)
                    }
                }

            }
        }
    }
}
