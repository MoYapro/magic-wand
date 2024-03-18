package de.moyapro.colors.wand.actions

import de.moyapro.colors.createExampleMage
import de.moyapro.colors.createExampleWand
import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.game.actions.GiveWandAction
import io.kotest.matchers.shouldBe
import org.junit.Test

class GiveWandActionTest {
    @Test
    fun `give a mage a wand`() {
        val wand = createExampleWand()
        val mage = createExampleMage()
        val state = MyGameState(
            wands =  listOf(wand),
            mages =  listOf(mage),
            currentTurn = 0,
            enemies = emptyList(),
            magicToPlay = emptyList()
        )
        state.findWand(mage.id) shouldBe null
        state.findMage(wand.id) shouldBe null

        val updatedState = GiveWandAction(wand.id, mage.id).apply(state).getOrThrow()
        updatedState.findWand(mage.id)?.id shouldBe wand.id
        updatedState.findMage(wand.id)?.id shouldBe mage.id

    }

    @Test
    fun `don't give a second wand`() {
        val wand = createExampleWand()
        val mage = createExampleMage()
        val state = MyGameState(
            wands =  listOf(wand),
            mages =  listOf(mage),
            currentTurn = 0,
            enemies = emptyList(),
            magicToPlay = emptyList()
        )
        val updatedState = GiveWandAction(wand.id, mage.id).apply(state).getOrThrow()
        val error = GiveWandAction(wand.id, mage.id).apply(updatedState)

        error.isFailure shouldBe true


    }
}