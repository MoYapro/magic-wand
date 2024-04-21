package de.moyapro.colors

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.takeTwo.*
import de.moyapro.colors.util.*

@Composable
fun WandEditView(
    modifier: Modifier = Modifier,
    wand: Wand = createExampleWand(),
    currentGameState: MyGameState,
    addAction: (GameAction) -> GameViewModel,
) {
    Column(
        modifier = modifier
            .height(5 * SPELL_SIZE.dp)
            .width(2 * SPELL_SIZE.dp)
    ) {
        val slotsByLevel =
            wand.slots.groupBy(Slot::level).toSortedMap { a, b -> b.compareTo(a) }
        slotsByLevel.forEach { (_, slots) ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                slots.forEach { slot ->
                    if (null == slot.spell) {
                        SlotEditView(wand.id, slot, currentGameState, addAction)
                    } else {
                        Draggable(
                            modifier = Modifier
                                .height(SPELL_SIZE.dp)
                                .width(SPELL_SIZE.dp),
                            dataToDrop = slot.spell,
                            onDropAction = RemoveSpellFromWandAction(wandId = wand.id, slotId = slot.id, spell = slot.spell),
                            onDropDidReplaceAction = { replacedSpell -> PlaceSpellAction(wand.id, slot.id, replacedSpell) }
                        ) {
                            SlotEditView(wand.id, slot, currentGameState, addAction)
                        }
                    }
                }
            }
        }
    }
}
