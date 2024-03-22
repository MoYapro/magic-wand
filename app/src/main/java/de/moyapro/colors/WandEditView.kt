package de.moyapro.colors

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import de.moyapro.colors.game.GameViewModel
import de.moyapro.colors.takeTwo.Slot
import de.moyapro.colors.takeTwo.Wand

@Composable
fun WandEditView(wandData: Wand = createExampleWand(), gameViewModel: GameViewModel) {
    val slotsByLevel =
        wandData.slots.groupBy(Slot::level).toSortedMap { a, b -> b.compareTo(a) }
    slotsByLevel.forEach { (_, slots) ->
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            slots.forEach { slot ->
                SlotEditView(wandData.id, slot, gameViewModel)
            }
        }
    }
}
