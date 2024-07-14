package de.moyapro.colors.game.actions.loot

import de.moyapro.colors.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.wand.*
import io.kotest.matchers.collections.*
import org.junit.*

class PlaceSpellInLootActionTest {
    @Test
    fun placeSpellInLoot() {
        val wand = createExampleWand()
        val targetSlot = wand.slots.single { it.spell?.name == "Blitz" }
        val spellToPlaceInLoot = targetSlot.spell!!
        val state = getExampleGameState()
        val action = PlaceSpellInLootAction(spellToPlaceInLoot)
        val updatedState = action.apply(state).getOrThrow()
        updatedState.currentRun.spells.map(Spell::name) shouldContain "Blitz"
        updatedState.currentRun.spells shouldHaveSize state.currentRun.spells.size + 1
    }
}
