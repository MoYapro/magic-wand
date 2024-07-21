package de.moyapro.colors.game.actions.loot

import de.moyapro.colors.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.accessor.*
import de.moyapro.colors.game.*
import io.kotest.matchers.*
import io.kotest.matchers.collections.*
import org.junit.*

class AddWandActionTest {
    @Test
    fun `should add wand`() {
        val wand1 = createExampleWand()
        val wand2 = createExampleWand()
        var state = getExampleGameState()
        state = RemoveWandAction(state.currentRun.activeWands.findWandOnMage(MAGE_I_ID)!!).apply(state).getOrThrow()
        state = RemoveWandAction(state.currentRun.activeWands.findWandOnMage(MAGE_II_ID)!!).apply(state).getOrThrow()
        state = AddWandAction(targetMageId = MAGE_I_ID, wandToAdd = wand1).apply(state).getOrThrow()
        state = AddWandAction(targetMageId = MAGE_II_ID, wandToAdd = wand2).apply(state).getOrThrow()
        state.currentRun.activeWands.map(Wand::id) shouldContainExactlyInAnyOrder listOf(
            wand1.id,
            wand2.id,
            state.currentRun.activeWands.findWandOnMage(MAGE_III_ID)!!.id
        )
        state.currentRun.findMage(wand1.id).id shouldBe MAGE_I_ID
        state.currentRun.findMage(wand2.id).id shouldBe MAGE_II_ID
    }
}
