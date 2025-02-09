package de.moyapro.colors.game.actions.stash

import de.moyapro.colors.wand.getExampleGameState
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe
import org.junit.Test

class RemoveWandFromStashActionTest {
    @Test
    fun `should remove wand from loot`() {
        val state = getExampleGameState()
        val wandToRemove = state.currentRun.wandsInBag.first()
        val updatedState = RemoveWandFromStashAction(wandToRemove).apply(state).getOrThrow()
        updatedState.currentRun.wandsInBag shouldNotContain wandToRemove
        updatedState.currentRun.activeWands shouldBe state.currentRun.activeWands
    }
}
