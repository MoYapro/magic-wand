package de.moyapro.colors.game.actions.stash

import de.moyapro.colors.*
import de.moyapro.colors.game.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.wand.getExampleGameState
import io.kotest.matchers.collections.*
import org.junit.*

class PlaceSpellInStashActionTest {
    @Test
    fun placeSpellInStash() {
        val targetSlot = createExampleWand().slots.first { it.spell?.name == "Bonk" }
        val spellToPlaceInStash = targetSlot.spell!!
        val state = getExampleGameState()
        val action = PlaceSpellInStashAction(spellToPlaceInStash)
        val updatedState = action.apply(state).getOrThrow()
        updatedState.currentRun.spells.map(Spell<*>::name) shouldContain "Bonk"
        updatedState.currentRun.spells shouldHaveSize state.currentRun.spells.size + 1
    }
}
