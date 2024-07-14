package de.moyapro.colors.game.actions.loot

import de.moyapro.colors.*
import de.moyapro.colors.wand.*
import io.kotest.matchers.collections.*
import org.junit.*

class RemoveSpellFromLootActionTest {
    @Test
    fun removeSpellFromLoot() {
        val wand = createExampleWand()
        val targetSlot = wand.slots.single { it.spell?.name == "Blitz" }
        val state = getExampleGameState()
        val spellToRemove = state.currentRun.spells.first()
        val action = RemoveSpellFromLootAction(spellToRemove)
        val updatedState = action.apply(state).getOrThrow()
        updatedState.currentRun.spells shouldNotContain spellToRemove
        updatedState.currentRun.spells shouldHaveSize state.currentRun.spells.size - 1
    }
}
