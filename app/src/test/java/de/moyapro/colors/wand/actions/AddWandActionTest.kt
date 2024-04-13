package de.moyapro.colors.wand.actions

import de.moyapro.colors.createExampleMage
import de.moyapro.colors.createExampleWand
import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.game.actions.AddWandAction
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldNotBe
import org.junit.Test

class AddWandActionTest {
    @Test
    fun `should remove wand`() {
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
                        mages = listOf(createExampleMage(), createExampleMage()),
                    )
                ).getOrThrow()
            ).getOrThrow()
        state.wands shouldContainExactlyInAnyOrder listOf(wand1, wand2)
        state.findMage(wand1.id) shouldNotBe null
        state.findMage(wand2.id) shouldNotBe null
    }
}
