package de.moyapro.colors

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.takeTwo.*
import de.moyapro.colors.util.*
import de.moyapro.colors.wand.*

@Composable
fun SlotView(
    wandId: WandId,
    slot: Slot = createExampleSlot(),
    addAction: (GameAction) -> GameViewModel,
    currentGameState: MyGameState,
) {
    DropZone<Magic>(
        modifier = Modifier
            .border(BorderStroke(1.dp, Color.LightGray))
            .size(SPELL_SIZE.dp),
        currentGameState = currentGameState,
        addAction = addAction,
        condition = { _, droppedMagic -> slot.canPlace(droppedMagic) },
        onDropAction = { droppedMagic -> PlaceMagicAction(wandId = wandId, slotId = slot.id, magicToPlace = droppedMagic) }
    ) { modifier: Modifier, isInBound: Boolean, hoveredMagic: Magic? ->
        Box(
            modifier = modifier.width(SPELL_SIZE.dp).height(SPELL_SIZE.dp),
        ) {
            PowerMeter(slot.power)
            if (slot.spell != null) SpellView(spell = slot.spell)
        }
    }
}
