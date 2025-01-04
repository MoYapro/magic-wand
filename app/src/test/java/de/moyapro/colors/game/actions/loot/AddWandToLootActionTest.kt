package de.moyapro.colors.game.actions.loot

import de.moyapro.colors.createExampleWand
import de.moyapro.colors.game.getExampleGameState
import de.moyapro.colors.game.model.Wand
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import org.junit.Test

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
