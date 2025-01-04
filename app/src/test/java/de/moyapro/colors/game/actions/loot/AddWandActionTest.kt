package de.moyapro.colors.game.actions.loot

import de.moyapro.colors.createExampleWand
import de.moyapro.colors.game.getExampleGameState
import de.moyapro.colors.game.model.Wand
import de.moyapro.colors.game.model.accessor.findMage
import de.moyapro.colors.game.model.accessor.findWandOnMage
import de.moyapro.colors.util.MAGE_III_ID
import de.moyapro.colors.util.MAGE_II_ID
import de.moyapro.colors.util.MAGE_I_ID
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import org.junit.Test

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
