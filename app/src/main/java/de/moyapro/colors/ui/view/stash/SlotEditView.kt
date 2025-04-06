package de.moyapro.colors.ui.view.stash

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.moyapro.colors.createExampleSlot
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.actions.stash.PlaceSpellAction
import de.moyapro.colors.game.model.Slot
import de.moyapro.colors.game.model.Spell
import de.moyapro.colors.game.model.WandId
import de.moyapro.colors.game.model.gameState.GameState
import de.moyapro.colors.ui.view.components.PowerMeter
import de.moyapro.colors.ui.view.components.SpellView
import de.moyapro.colors.ui.view.dragdrop.DropZone
import de.moyapro.colors.util.SPELL_SIZE

@Composable
fun SlotEditView(
    wandId: WandId,
    slot: Slot = createExampleSlot(),
    currentGameState: GameState,
    addAction: (GameAction) -> Unit,
    dropZoneDisabled: Boolean = false,
) {
    if (dropZoneDisabled)
        SpellView(spell = slot.spell)
    else
        DropZone<Spell<*>>(
            modifier = Modifier
                .border(BorderStroke(1.dp, Color.LightGray))
                .width(SPELL_SIZE.dp)
                .height(SPELL_SIZE.dp),
            condition = { _, droppedSpell -> slot.spell?.id != droppedSpell.id },
            onDropAction = { droppedSpell -> PlaceSpellAction(wandId, slot.id, droppedSpell) },
            currentGameState = currentGameState,
            addAction = addAction,
            emitData = slot.spell
        )
        { modifier: Modifier, _: Boolean, _: Any? ->
            Box(
                modifier = modifier
                    .width(SPELL_SIZE.dp)
                    .height(SPELL_SIZE.dp),
            ) {
                PowerMeter(SPELL_SIZE, slot.power)
                if (slot.spell != null) SpellView(spell = slot.spell)
            }
        }
}
