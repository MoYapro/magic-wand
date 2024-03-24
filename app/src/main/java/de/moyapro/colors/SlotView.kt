package de.moyapro.colors

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import de.moyapro.colors.game.GameViewModel
import de.moyapro.colors.game.actions.PlaceMagicAction
import de.moyapro.colors.takeTwo.Slot
import de.moyapro.colors.takeTwo.WandId
import de.moyapro.colors.wand.Magic
import de.moyapro.colors.wand.MagicSlot

@Composable
fun SlotView(wandId: WandId, slot: Slot = createExampleSlot(), gameViewModel: GameViewModel) {
    DropZone<Magic>(
        modifier = Modifier.border(BorderStroke(1.dp, Color.LightGray)),
        gameViewModel = gameViewModel,
    ) { isInBound: Boolean, droppedMagic: Magic?, hoveredMagic: Magic? ->
        val isThisSlotFull = slot.magicSlots.none { it.placedMagic == null }
        val hoveredMagicDoesFit =
            !isThisSlotFull && slot.magicSlots.any { it.requiredMagic.type == hoveredMagic?.type && it.placedMagic == null }
        val localContext = LocalContext.current
        if (droppedMagic != null && hoveredMagicDoesFit) {
            LaunchedEffect(key1 = droppedMagic) {
                gameViewModel.addAction(PlaceMagicAction(wandId, slot.id, droppedMagic))
                Toast.makeText(localContext, droppedMagic.toString(), Toast.LENGTH_LONG).show()
            }
        }
        val usedColor: Color = when {
            !isInBound -> Color.Transparent
            isInBound && !hoveredMagicDoesFit -> Color.Red
            isInBound && hoveredMagicDoesFit -> Color.Green
            else -> throw IllegalStateException("cannot determine hover color")
        }

        Box(modifier = Modifier.background(usedColor)) {
            Row {
                Text("".padStart(slot.power, '|'))
                Text(hoveredMagic?.type?.symbol?.toString() ?: "")
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
