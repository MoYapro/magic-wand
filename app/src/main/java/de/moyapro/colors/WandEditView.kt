package de.moyapro.colors

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import de.moyapro.colors.game.GameViewModel
import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.takeTwo.Slot
import de.moyapro.colors.takeTwo.Wand
import de.moyapro.colors.util.DROP_ZONE_ALPHA
import de.moyapro.colors.util.SPELL_SIZE

@Composable
fun WandEditView(
    modifier: Modifier = Modifier,
    wand: Wand = createExampleWand(),
    currentGameState: MyGameState,
    mainViewModel: MainViewModel,
    addAction: (GameAction) -> GameViewModel,
) {
    DropZone(currentGameState = currentGameState) { isInBound, dropData, hoverData ->
        val color = if (isInBound) Color.Green.copy(DROP_ZONE_ALPHA) else Color.Transparent
        Column(
            modifier = modifier
                .height(4 * SPELL_SIZE.dp)
                .width(2 * SPELL_SIZE.dp)
                .background(color)
        ) {
            val slotsByLevel =
                wand.slots.groupBy(Slot::level).toSortedMap { a, b -> b.compareTo(a) }
            slotsByLevel.forEach { (_, slots) ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    slots.forEach { slot ->
                        Draggable(
                            modifier = Modifier
                                .height(SPELL_SIZE.dp)
                                .width(SPELL_SIZE.dp),
                            dataToDrop = slot.spell,
                            mainViewModel = mainViewModel
                        ) {
                            SlotEditView(wand.id, slot, currentGameState, addAction)
                        }
                    }
                }
            }
        }
    }
}
