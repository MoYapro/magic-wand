package de.moyapro.colors.wand.actions

import de.moyapro.colors.createExampleMage
import de.moyapro.colors.createExampleWand
import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.game.actions.AddWandAction
import de.moyapro.colors.game.actions.RemoveWandAction
import io.kotest.matchers.shouldBe
import org.junit.Test

class RemoveWandActionTest {
    @Test
    fun `should remove wand`() {
        val wand1 = createExampleWand()
        val wand2 = createExampleWand()
        val mage = createExampleMage(health = 10, wandId = wand1.id)
        val state =
            AddWandAction(wand1).apply(
                AddWandAction(wand2).apply(
                    MyGameState(
                        currentTurn = 0,
                        wands = emptyList(),
                        magicToPlay = emptyList(),
                        enemies = emptyList(),
                        mages = listOf(mage),
                    )
                ).getOrThrow()
            ).getOrThrow()
        val updatedState = RemoveWandAction(wand1).apply(state).getOrThrow()
        updatedState.wands.single() shouldBe wand2
        updatedState.findMage(wand1.id) shouldBe null
    }
}