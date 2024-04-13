package de.moyapro.colors.wand.actions

import de.moyapro.colors.createExampleEnemy
import de.moyapro.colors.createExampleMage
import de.moyapro.colors.createExampleWand
import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.game.actions.AddWandAction
import de.moyapro.colors.game.actions.HitAction
import de.moyapro.colors.game.actions.RemoveWandAction
import io.kotest.matchers.shouldBe
import org.junit.Test

class RemoveWandActionTest {
    @Test
    fun `should remove wand`() {
        val mage = createExampleMage(10)
        val wand1 = createExampleWand()
        val wand2 = createExampleWand()
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
        val updatedState = RemoveWandAction(wand1).apply(state)
        updatedState.getOrThrow().wands.single() shouldBe wand2
    }

    @Test
    fun `min health is zero`() {
        val exampleEnemy = createExampleEnemy()
        val mage = createExampleMage(10)
        val state = MyGameState(
            currentTurn = 0,
            wands = emptyList(),
            magicToPlay = emptyList(),
            enemies = listOf(exampleEnemy),
            mages = listOf(mage),
        )
        val updatedState = HitAction(mage.id, damage = 99).apply(state)
        updatedState.getOrThrow().mages.first().health shouldBe 0
    }
}
