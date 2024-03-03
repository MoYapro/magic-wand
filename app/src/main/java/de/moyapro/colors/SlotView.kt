package de.moyapro.colors

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import de.moyapro.colors.game.GameViewModel
import de.moyapro.colors.takeTwo.Slot
import de.moyapro.colors.takeTwo.WandId

@Preview
@Composable
fun SlotView(wandId: WandId, slot: Slot = createExampleSlot(), gameViewModel: GameViewModel) {
    Row {
        Text("".padStart(slot.power, '|'))
        Text(slot.spell?.spellName ?: "empty")
        slot.magicSlots.forEach { magicSlot ->
            MagicSlotView(wandId, slot.id, magicSlot, gameViewModel)
        }
    }
}
