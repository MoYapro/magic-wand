package de.moyapro.colors

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import de.moyapro.colors.game.GameViewModel
import de.moyapro.colors.game.actions.TargetSelectedAction
import de.moyapro.colors.game.actions.ZapAction
import de.moyapro.colors.takeTwo.Slot
import de.moyapro.colors.takeTwo.Wand

@Preview
@Composable
fun WandView(wandData: Wand = createExampleWand(), gameViewModel: GameViewModel) {
    Column {
        Button(onClick = {
            gameViewModel
                .addAction(ZapAction(wandData.id))
        }
        ) {
            Text("Zap")
        }
        val slotsByLevel =
            wandData.slots.groupBy(Slot::level).toSortedMap { a, b -> b.compareTo(a) }
        slotsByLevel.forEach { (_, slots) ->
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                slots.forEach { slot ->
                    SlotView(wandData.id, slot, gameViewModel)
                }
            }
        }
    }
}
