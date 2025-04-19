package de.moyapro.colors.ui.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.moyapro.colors.createExampleSlot
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.actions.fight.PlaceMagicAction
import de.moyapro.colors.game.generators.Initializer
import de.moyapro.colors.game.model.Magic
import de.moyapro.colors.game.model.Slot
import de.moyapro.colors.game.model.WandId
import de.moyapro.colors.game.model.gameState.GameState
import de.moyapro.colors.game.spell.Fizz
import de.moyapro.colors.game.spell.Splash
import de.moyapro.colors.ui.view.dragdrop.DropZone
import de.moyapro.colors.util.SPELL_SIZE
import de.moyapro.colors.R

@Composable
fun SlotView(
    wandId: WandId,
    slot: Slot = createExampleSlot(),
    addAction: (GameAction) -> Unit,
    currentGameState: GameState,
) {
    DropZone<Magic>(
        modifier = Modifier.size(SPELL_SIZE.dp),
        currentGameState = currentGameState,
        addAction = addAction,
        condition = { _, droppedMagic -> slot.canPlace(droppedMagic) },
        onDropAction = { droppedMagic -> PlaceMagicAction(wandId = wandId, slotId = slot.id, magicToPlace = droppedMagic) }
    ) { modifier: Modifier, _: Boolean, _: Magic? ->
        Box(
            modifier = modifier
                .size(SPELL_SIZE.dp)
        ) {
            Image(
                modifier = modifier.size(SPELL_SIZE.dp),
                painter = painterResource(id = R.drawable.empty_spell_slot),
                contentDescription = "slot background"
            )
            SpellView(spell = slot.spell)
            PowerMeter(SPELL_SIZE, slot.power)
        }
    }
}

@Composable
@Preview
fun SlotViewPreview() {
    Column {
        SlotView(
            wandId = WandId(),
            slot = createExampleSlot(spell = null),
            addAction = {},
            currentGameState = Initializer.createInitialGameState()
        )
        SlotView(
            wandId = WandId(),
            slot = createExampleSlot(spell = Fizz()),
            addAction = {},
            currentGameState = Initializer.createInitialGameState()
        )
        SlotView(
            wandId = WandId(),
            slot = createExampleSlot(power = 4, spell = Splash()),
            addAction = {},
            currentGameState = Initializer.createInitialGameState()
        )
    }
}
