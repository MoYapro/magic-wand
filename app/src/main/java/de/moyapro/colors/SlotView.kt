package de.moyapro.colors

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import de.moyapro.colors.game.GameViewModel
import de.moyapro.colors.takeTwo.Slot
import de.moyapro.colors.takeTwo.WandId

@Preview
@Composable
fun SlotView(wandId: WandId, slot: Slot = createExampleSlot(), gameViewModel: GameViewModel) {
    val isThisSlotFull = slot.magicSlots.none { it.placedMagic == null }
    Box(modifier = Modifier.background(if (isThisSlotFull) Color.Magenta else Color.Transparent)) {
        Row {
            Text("".padStart(slot.power, '|'))
            Text(slot.spell?.spellName ?: "empty")
            slot.magicSlots.forEach { magicSlot ->
                MagicSlotView(wandId, slot.id, magicSlot, gameViewModel)
            }
        }
    }
}
