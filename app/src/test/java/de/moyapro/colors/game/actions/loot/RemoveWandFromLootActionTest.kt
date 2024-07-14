package de.moyapro.colors.game.actions.loot

import de.moyapro.colors.wand.*
import io.kotest.matchers.*
import io.kotest.matchers.collections.*
import org.junit.*

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
