package de.moyapro.colors

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.takeTwo.*
import de.moyapro.colors.util.*
import de.moyapro.colors.wand.*

@Composable
fun SlotEditView(
    wandId: WandId,
    slot: Slot = createExampleSlot(),
    currentGameState: MyGameState,
    addAction: (GameAction) -> GameViewModel,
) {
    DropZone<Any>(
        modifier = Modifier
            .border(BorderStroke(1.dp, Color.LightGray))
            .fillMaxSize(),
        condition = { _, dropData -> dropData != null && dropData is Spell && slot.spell?.id != dropData.id },
        currentGameState = currentGameState,
        addAction = addAction,
    )
    { isInBound: Boolean, droppedSpell: Any?, _: Any? ->
        if (droppedSpell != null && droppedSpell !is Spell) {
            return@DropZone
        }
        if (droppedSpell != null) {
            LaunchedEffect(key1 = droppedSpell) {
                addAction(PlaceSpellAction(wandId, slot.id, droppedSpell as Spell))
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
                        items = slot.spell?.magicSlots ?: emptyList(),
                        key = { magicSlot: MagicSlot -> magicSlot.id.hashCode() }) { magicSlot: MagicSlot ->
                        MagicSlotView(magicSlot)
                    }
                }

            }
        }
    }
}
