package de.moyapro.colors

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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

@Composable
fun SlotView(wandId: WandId, slot: Slot = createExampleSlot(), gameViewModel: GameViewModel) {
    DropZone<Magic>(
        modifier = Modifier.border(BorderStroke(1.dp, Color.LightGray))
    ) { isInBound: Boolean, droppedMagic: Magic? ->
        val isThisSlotFull = slot.magicSlots.none { it.placedMagic == null }
        val localContext = LocalContext.current
        if (droppedMagic != null && !isThisSlotFull) {
            LaunchedEffect(key1 = droppedMagic) {
                gameViewModel.addAction(PlaceMagicAction(wandId, slot.id, droppedMagic))
                Toast.makeText(localContext, droppedMagic.toString(), Toast.LENGTH_LONG).show()
            }
        }


        Box(modifier = Modifier.background(if (isThisSlotFull) Color.Magenta else Color.Transparent)) {
            Row {
                Text("".padStart(slot.power, '|'))
                Text(slot.spell?.spellName ?: "empty")
                slot.magicSlots.forEach { magicSlot -> MagicSlotView(magicSlot) }
            }
        }
    }
}
