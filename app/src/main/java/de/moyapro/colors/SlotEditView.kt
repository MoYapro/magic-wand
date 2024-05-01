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
fun SlotEditView(
    wandId: WandId,
    slot: Slot = createExampleSlot(),
    currentGameState: MyGameState,
    addAction: (GameAction) -> GameViewModel,
    dropZoneDisabled: Boolean = false,
) {
    if (dropZoneDisabled)
        SpellView(spell = slot.spell)
    else
        DropZone<Spell>(
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
        { modifier: Modifier, isInBound: Boolean, _: Any? ->
            Box(
                modifier = modifier.width(SPELL_SIZE.dp).height(SPELL_SIZE.dp),
            ) {
                PowerMeter(slot.power)
                if (slot.spell != null) SpellView(spell = slot.spell)
            }
        }
}

@Composable
private fun PowerMeter(power: Int) {
    val fives = power / 5
    val ones = power % 5
    Row {
        Spacer(modifier = Modifier.width(1.dp))
        Column(modifier = Modifier.height(SPELL_SIZE.dp), verticalArrangement = Arrangement.Bottom) {
            repeat(fives) {
                Box(modifier = Modifier.width(8.dp).height(6.dp).background(Color.White))
                Spacer(modifier = Modifier.height(2.dp))
            }
            repeat(ones) {
                Box(modifier = Modifier.width(8.dp).height(2.dp).background(Color.White))
                Spacer(modifier = Modifier.height(2.dp))
            }
        }
    }
}
