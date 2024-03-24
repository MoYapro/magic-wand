package de.moyapro.colors

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import de.moyapro.colors.game.GameViewModel
import de.moyapro.colors.game.actions.ZapAction
import de.moyapro.colors.takeTwo.Slot
import de.moyapro.colors.takeTwo.Wand
import de.moyapro.colors.util.SPELL_SIZE

@Composable
fun WandView(
    modifier: Modifier = Modifier,
    wand: Wand = createExampleWand(),
    gameViewModel: GameViewModel,
) {
    Column(modifier = modifier.height(4 * SPELL_SIZE.dp)) {
        Button(onClick = {
            gameViewModel
                .addAction(ZapAction(wand.id))
        }
        ) {
            Text("Zap")
        }
        val slotsByLevel =
            wand.slots.groupBy(Slot::level).toSortedMap { a, b -> b.compareTo(a) }
        slotsByLevel.forEach { (_, slots) ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                slots.forEach { slot ->
                    SlotView(wand.id, slot, gameViewModel)
                }
            }
        }
    }
}
