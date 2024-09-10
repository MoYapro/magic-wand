package de.moyapro.colors.game.actions.loot

import de.moyapro.colors.game.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.accessor.*
import io.kotest.matchers.*
import org.junit.*

class PlaceSpellActionTest {
    @Test
    fun `place spell in wand at hand`() {
        val state = getExampleGameState()
        val spellToPlace = Bonk()
        val wandToPutSpellInto = state.currentRun.activeWands.first()
        val slotToPutSpellInto = wandToPutSpellInto.slots.first()
        val action = PlaceSpellAction(slotId = slotToPutSpellInto.id, spell = spellToPlace, wandId = wandToPutSpellInto.id)
        val updatedState = action.apply(state).getOrThrow()
        updatedState.currentRun.activeWands.findWand(wandToPutSpellInto.id)!!.slots.single { it.id == slotToPutSpellInto.id }.spell?.name shouldBe "newSpell"
    }

    @Test
    fun `place spell in wand in loot`() {
        val spellToPlace = Bonk()
        val state = getExampleGameState()
        val wandToPutSpellInto = state.currentRun.wandsInBag.first()
        val slotToPutSpellInto = wandToPutSpellInto.slots.first()
        val action = PlaceSpellAction(slotId = slotToPutSpellInto.id, spell = spellToPlace, wandId = wandToPutSpellInto.id)
        val updatedState = action.apply(state).getOrThrow()
        updatedState.currentRun.wandsInBag.findWand(wandToPutSpellInto.id)!!.slots.single { it.id == slotToPutSpellInto.id }.spell?.name shouldBe "newSpell"
    }

}
