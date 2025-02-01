package de.moyapro.colors.ui.view.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.moyapro.colors.createExampleSlot
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.actions.fight.PlaceMagicAction
import de.moyapro.colors.game.model.Magic
import de.moyapro.colors.game.model.Slot
import de.moyapro.colors.game.model.WandId
import de.moyapro.colors.game.model.gameState.GameState
import de.moyapro.colors.ui.view.dragdrop.DropZone
import de.moyapro.colors.util.SPELL_SIZE

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
    ) { modifier: Modifier, _: Boolean, _: Magic? ->
        Box(
            modifier = modifier
                .width(SPELL_SIZE.dp)
                .height(SPELL_SIZE.dp),
        ) {
            PowerMeter(slot.power, SPELL_SIZE)
            if (slot.spell != null) SpellView(spell = slot.spell)
        }
    }
}
