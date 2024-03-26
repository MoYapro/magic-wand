package de.moyapro.colors

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.moyapro.colors.game.GameViewModel
import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.actions.PlaceSpellAction
import de.moyapro.colors.takeTwo.Slot
import de.moyapro.colors.takeTwo.WandId
import de.moyapro.colors.util.DROP_ZONE_ALPHA
import de.moyapro.colors.wand.MagicSlot
import de.moyapro.colors.wand.Spell

@Composable
fun SlotEditView(
    wandId: WandId,
    slot: Slot = createExampleSlot(),
    currentGameState: MyGameState,
    addAction: (GameAction) -> GameViewModel,
) {
    DropZone<Spell>(
        modifier = Modifier
            .border(BorderStroke(1.dp, Color.LightGray))
            .fillMaxSize(),
        condition = { _, dropData -> dropData != null && slot.spell?.id != dropData.id },
        currentGameState = currentGameState,
        { isInBound: Boolean, droppedSpell: Spell?, _: Spell? ->
            if (droppedSpell != null) {
                LaunchedEffect(key1 = droppedSpell) {
                    addAction(PlaceSpellAction(wandId, slot.id, droppedSpell))
                }
            }
            val usedColor: Color = when {
                !isInBound -> Color.Transparent
                isInBound -> Color.Green.copy(alpha = DROP_ZONE_ALPHA)
                else -> throw IllegalStateException("cannot determine hover color")
            }

            Box(
                modifier = Modifier
                    .background(usedColor)
                    .fillMaxSize()
            ) {
                Row {
                    Text("".padStart(slot.power, '|'))
                    Text(slot.spell?.name ?: "empty")
                    LazyRow(userScrollEnabled = false) {
                        items(
                            items = slot.magicSlots,
                            key = { magicSlot: MagicSlot -> magicSlot.id.hashCode() }) { magicSlot: MagicSlot ->
                            MagicSlotView(magicSlot)
                        }
                    }

                }
            }
        },
    )
}
