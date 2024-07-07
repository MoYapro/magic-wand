package de.moyapro.colors.wand.actions

import de.moyapro.colors.game.actions.loot.*
import de.moyapro.colors.game.model.accessor.*
import de.moyapro.colors.wand.*
import io.kotest.matchers.*
import io.kotest.matchers.collections.*
import org.junit.*

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
