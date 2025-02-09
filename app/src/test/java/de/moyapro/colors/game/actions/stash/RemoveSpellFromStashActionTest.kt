package de.moyapro.colors.game.actions.stash

import de.moyapro.colors.wand.getExampleGameState
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.collections.shouldNotContain
import org.junit.Test

class RemoveSpellFromStashActionTest {
    @Test
    fun removeSpellFromStash() {
        val state = getExampleGameState()
        val spellToRemove = state.currentRun.spells.first()
        val action = RemoveSpellFromStashAction(spellToRemove)
        val updatedState = action.apply(state).getOrThrow()
        updatedState.currentRun.spells shouldNotContain spellToRemove
        updatedState.currentRun.spells shouldHaveSize state.currentRun.spells.size - 1
    }
}
