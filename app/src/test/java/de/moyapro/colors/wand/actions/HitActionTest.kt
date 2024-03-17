package de.moyapro.colors.wand.actions

import de.moyapro.colors.createExampleEnemy
import de.moyapro.colors.createExampleMage
import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.game.actions.HitAction
import io.kotest.matchers.shouldBe
import org.junit.Test

class HitActionTest {
    @Test
    fun `hitting a mage reduces health`() {
        val exampleEnemy = createExampleEnemy()
        val mage = createExampleMage(10)
        val state = MyGameState(
            currentTurn = 0,
            wands = emptyList(),
            magicToPlay = emptyList(),
            enemies = listOf(exampleEnemy),
            mage = mage,
        )
        val updatedState = HitAction(mage.id).apply(state)
        updatedState.getOrThrow().mage!!.health shouldBe 9
    }
}