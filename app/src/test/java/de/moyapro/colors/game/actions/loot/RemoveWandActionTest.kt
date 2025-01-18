package de.moyapro.colors.game.actions.loot

import de.moyapro.colors.game.model.accessor.findMage
import de.moyapro.colors.game.model.accessor.findWandOnMage
import de.moyapro.colors.util.MAGE_I_ID
import de.moyapro.colors.wand.getExampleGameState
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe
import org.junit.Test

class RemoveWandActionTest {
    @Test
    fun `should remove wand`() {
        val state = getExampleGameState()
        val wandToRemove = state.currentRun.activeWands.findWandOnMage(MAGE_I_ID)!!
        val wandsToKeep = state.currentRun.activeWands.filter { it.id != wandToRemove.id }
        val updatedState = RemoveWandAction(wandToRemove).apply(state).getOrThrow()
        updatedState.currentRun.activeWands shouldNotContain wandToRemove
        updatedState.currentRun.activeWands shouldContainExactlyInAnyOrder wandsToKeep
        updatedState.currentRun.mages.findMage(MAGE_I_ID)!!.wandId shouldBe null
    }
}
