package de.moyapro.colors.game.actions.stash

import de.moyapro.colors.createExampleWand
import de.moyapro.colors.game.model.Wand
import de.moyapro.colors.wand.getExampleGameState
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import org.junit.Test

class AddWandToStashActionTest {
    @Test
    fun `should add wand to loot`() {
        val wandToAdd = createExampleWand()
        val state = getExampleGameState()
        val updatedState = AddWandToStashAction(wand = wandToAdd).apply(state).getOrThrow()
        updatedState.currentRun.wandsInBag.map(Wand::id) shouldContainExactlyInAnyOrder listOf(
            *state.currentRun.wandsInBag.toTypedArray(),
            wandToAdd
        ).map(Wand::id)
    }
}
