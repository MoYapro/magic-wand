package de.moyapro.colors.game.actions.stash

import de.moyapro.colors.createExampleWand
import de.moyapro.colors.game.model.Spell
import de.moyapro.colors.game.spell.Bonk
import de.moyapro.colors.util.MAGE_I_ID
import de.moyapro.colors.wand.getExampleGameState
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import org.junit.Test

class PlaceSpellInStashActionTest {
    @Test
    fun placeSpellInStash() {
        val targetSlot = createExampleWand(MAGE_I_ID, Bonk()).slots.first { it.spell?.name == "Bonk" }
        val spellToPlaceInStash = targetSlot.spell!!
        val state = getExampleGameState()
        val action = PlaceSpellInStashAction(spellToPlaceInStash)
        val updatedState = action.apply(state).getOrThrow()
        updatedState.currentRun.spells.map(Spell<*>::name) shouldContain "Bonk"
        updatedState.currentRun.spells shouldHaveSize state.currentRun.spells.size + 1
    }
}
