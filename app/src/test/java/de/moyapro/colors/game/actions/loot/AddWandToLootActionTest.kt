package de.moyapro.colors.game.actions.loot

import de.moyapro.colors.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.wand.*
import io.kotest.matchers.collections.*
import org.junit.*

class AddWandToLootActionTest {
    @Test
    fun `should add wand to loot`() {
        val wandToAdd = createExampleWand()
        val state = getExampleGameState()
        val updatedState = AddWandToLootAction(wand = wandToAdd).apply(state).getOrThrow()
        updatedState.currentRun.wandsInBag.map(Wand::id) shouldContainExactlyInAnyOrder listOf(
            *state.currentRun.wandsInBag.toTypedArray(),
            wandToAdd
        ).map(Wand::id)
    }
}
