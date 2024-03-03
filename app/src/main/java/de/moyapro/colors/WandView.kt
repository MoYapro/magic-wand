package de.moyapro.colors

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import de.moyapro.colors.game.GameViewModel
import de.moyapro.colors.takeTwo.Slot
import de.moyapro.colors.takeTwo.Wand

@Preview
@Composable
fun WandView(wandData: Wand = createExampleWand(), gameViewModel: GameViewModel) {
    Column {
        Text("I'm a Wand")
        val slotsByLevel = wandData.slots.groupBy(Slot::level).toSortedMap{a, b -> b.compareTo(a) }
        slotsByLevel.forEach { (_, slots) ->
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                slots.forEach { slot ->
                    SlotView(wandData.id, slot, gameViewModel)
                }
            }
        }
    }
}
