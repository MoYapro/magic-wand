package de.moyapro.colors.game.actions.stash

import de.moyapro.colors.game.model.accessor.findWand
import de.moyapro.colors.game.spell.Bonk
import de.moyapro.colors.wand.getExampleGameState
import io.kotest.matchers.shouldBe
import org.junit.Test

class PlaceSpellActionTest {
    @Test
    fun `place spell in wand at hand`() {
        val state = getExampleGameState()
        val spellToPlace = Bonk()
        val wandToPutSpellInto = state.currentRun.activeWands.first()
        val slotToPutSpellInto = wandToPutSpellInto.slots.first()
        val action = PlaceSpellAction(slotId = slotToPutSpellInto.id, spell = spellToPlace, wandId = wandToPutSpellInto.id)
        val updatedState = action.apply(state).getOrThrow()
        updatedState.currentRun.activeWands.findWand(wandToPutSpellInto.id)!!.slots.single { it.id == slotToPutSpellInto.id }.spell?.name shouldBe "Bonk"
    }

    @Test
    fun `place spell in wand in stash`() {
        val spellToPlace = Bonk()
        val state = getExampleGameState()
        val wandToPutSpellInto = state.currentRun.wandsInBag.first()
        val slotToPutSpellInto = wandToPutSpellInto.slots.first()
        val action = PlaceSpellAction(slotId = slotToPutSpellInto.id, spell = spellToPlace, wandId = wandToPutSpellInto.id)
        val updatedState = action.apply(state).getOrThrow()
        updatedState.currentRun.wandsInBag.findWand(wandToPutSpellInto.id)!!.slots.single { it.id == slotToPutSpellInto.id }.spell?.name shouldBe "Bonk"
    }

}
