package de.moyapro.colors

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.moyapro.colors.game.GameViewModel
import de.moyapro.colors.takeTwo.Slot
import de.moyapro.colors.takeTwo.Wand
import de.moyapro.colors.util.SPELL_SIZE

@Composable
fun WandEditView(
    modifier: Modifier = Modifier,
    wandData: Wand = createExampleWand(),
    gameViewModel: GameViewModel,
    mainViewModel: MainViewModel,
) {
    Column(modifier = modifier) {
        val slotsByLevel =
            wandData.slots.groupBy(Slot::level).toSortedMap { a, b -> b.compareTo(a) }
        slotsByLevel.forEach { (_, slots) ->
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround) {
                slots.forEach { slot ->
                    Draggable(
                        modifier = Modifier
                            .height(SPELL_SIZE.dp)
                            .width(SPELL_SIZE.dp),
                        dataToDrop = slot.spell,
                        viewModel = mainViewModel
                    ) {
                        SlotEditView(wandData.id, slot, gameViewModel)
                    }
                }
            }
        }
    }
}
