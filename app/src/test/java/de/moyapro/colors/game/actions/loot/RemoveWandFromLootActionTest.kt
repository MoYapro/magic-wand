package de.moyapro.colors.game.actions.loot

import de.moyapro.colors.game.getExampleGameState
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe
import org.junit.Test

class RemoveWandFromLootActionTest {
    @Test
    fun `should remove wand from loot`() {
        val state = getExampleGameState()
        val wandToRemove = state.currentRun.wandsInBag.first()
        val updatedState = RemoveWandFromLootAction(wandToRemove).apply(state).getOrThrow()
        updatedState.currentRun.wandsInBag shouldNotContain wandToRemove
        updatedState.currentRun.activeWands shouldBe state.currentRun.activeWands
    }
}
