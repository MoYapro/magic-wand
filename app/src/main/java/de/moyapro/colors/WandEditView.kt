package de.moyapro.colors

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import de.moyapro.colors.game.GameViewModel
import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.game.actions.CombinedAction
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.actions.RemoveWandAction
import de.moyapro.colors.takeTwo.Slot
import de.moyapro.colors.takeTwo.Wand
import de.moyapro.colors.util.DROP_ZONE_ALPHA
import de.moyapro.colors.util.SPELL_SIZE
import de.moyapro.colors.util.castOrNull

@Composable
fun WandEditView(
    modifier: Modifier = Modifier,
    wand: Wand = createExampleWand(),
    currentGameState: MyGameState,
    mainViewModel: MainViewModel,
    addAction: (GameAction) -> GameViewModel,
) {
    DropZone(
        currentGameState = currentGameState,
        condition = { gameState, maybeWand ->
            maybeWand != null && !gameState.wands.contains(maybeWand)

        }
    ) { isInBound, dropData, hoverData ->
        val newWand: Wand? = castOrNull(dropData)
        if (newWand != null) {
            addAction(
                CombinedAction(
//                    RemoveWandAction(wand),
//                    AddWandToLootAction(wand),
//                    AddWandAction(newWand)
                )
            )
        }

        val color = if (isInBound) Color.Green.copy(DROP_ZONE_ALPHA) else Color.Transparent
        Column(
            modifier = modifier
                .height(5 * SPELL_SIZE.dp)
                .width(2 * SPELL_SIZE.dp)
                .background(color)
        ) {
            Button(onClick = { addAction(RemoveWandAction(wand)) }) {
                Text("rm")
            }
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
