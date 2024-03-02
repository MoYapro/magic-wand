package de.moyapro.colors

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import de.moyapro.colors.takeTwo.Slot

@Preview
@Composable
fun SlotView(slot: Slot = createExampleSlot()) {
    Row {
        Text("".padStart(slot.power, '|'))
        Text(slot.spell?.spellName ?: "empty")
        slot.magicSlots.forEach {
            MagicSlotView(it)
        }
    }
}
