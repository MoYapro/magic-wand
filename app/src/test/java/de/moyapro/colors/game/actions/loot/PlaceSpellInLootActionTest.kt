package de.moyapro.colors.game.actions.loot

import de.moyapro.colors.*
import de.moyapro.colors.game.*
import de.moyapro.colors.game.model.*
import io.kotest.matchers.collections.*
import org.junit.*

class PlaceSpellInLootActionTest {
    @Test
    fun placeSpellInLoot() {
        val targetSlot = createExampleWand().slots.first { it.spell?.name == "Bonk" }
        val spellToPlaceInLoot = targetSlot.spell!!
        val state = getExampleGameState()
        val action = PlaceSpellInLootAction(spellToPlaceInLoot)
        val updatedState = action.apply(state).getOrThrow()
        updatedState.currentRun.spells.map(Spell<*>::name) shouldContain "Bonk"
        updatedState.currentRun.spells shouldHaveSize state.currentRun.spells.size + 1
    }
}
