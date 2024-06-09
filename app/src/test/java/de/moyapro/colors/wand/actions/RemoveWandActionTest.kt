package de.moyapro.colors.wand.actions

import de.moyapro.colors.*
import de.moyapro.colors.game.actions.loot.*
import de.moyapro.colors.game.model.*
import io.kotest.matchers.*
import org.junit.*

class RemoveWandActionTest {
    @Test
    fun `should remove wand`() {
        val wand1 = createExampleWand()
        val wand2 = createExampleWand()
        val mage1 = createExampleMage(health = 10, wandId = wand1.id, mageId = MageId(0))
        val mage2 = createExampleMage(health = 10, wandId = wand1.id, mageId = MageId(1))
        var state = MyGameState(
            currentTurn = 0,
            wands = emptyList(),
            magicToPlay = emptyList(),
            enemies = emptyList(),
            mages = listOf(mage1, mage2),
        )
        state = AddWandAction(wand1, mage1.id).apply(state).getOrThrow()
        state = AddWandAction(wand2, mage2.id).apply(state).getOrThrow()

        val updatedState = RemoveWandAction(wand1).apply(state).getOrThrow()
        updatedState.wands.single().id shouldBe wand2.id
        updatedState.findMage(wand1.id) shouldBe null
    }
}
