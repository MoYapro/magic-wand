package de.moyapro.colors.ui.view.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import de.moyapro.colors.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.actions.fight.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.gameState.*
import de.moyapro.colors.ui.view.dragdrop.*
import de.moyapro.colors.util.*

@Composable
fun SlotView(
    wandId: WandId,
    slot: Slot = createExampleSlot(),
    addAction: (GameAction) -> Unit,
    currentGameState: GameState,
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
            modifier = modifier
                .width(SPELL_SIZE.dp)
                .height(SPELL_SIZE.dp),
        ) {
            PowerMeter(slot.power)
            if (slot.spell != null) SpellView(spell = slot.spell)
        }
    }
}
