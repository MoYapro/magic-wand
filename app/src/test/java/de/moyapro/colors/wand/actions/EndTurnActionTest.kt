package de.moyapro.colors.wand.actions

import de.moyapro.colors.createExampleEnemy
import de.moyapro.colors.createExampleMagic
import de.moyapro.colors.createExampleWand
import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.game.actions.EndTurnAction
import de.moyapro.colors.game.actions.SelfHealEnemyAction
import io.kotest.matchers.equality.shouldBeEqualUsingFields
import io.kotest.matchers.shouldBe
import org.junit.Test

class EndTurn {
    @Test
    fun endTurn() {
        val state = MyGameState(
            enemies = listOf(createExampleEnemy()),
            wands = listOf(createExampleWand()),
            magicToPlay = listOf(createExampleMagic()),
            currentTurn = 0,
        )
        val endTurnAction = EndTurnAction()
        val nextTurnState1 = endTurnAction.apply(state)
        val nextTurnState2 = endTurnAction.apply(state)
        nextTurnState1 shouldBeEqualUsingFields nextTurnState2
        nextTurnState1.getOrThrow().currentTurn shouldBe 1
    }

    @Test
    fun bar() {
        SelfHealEnemyAction() shouldBe SelfHealEnemyAction()
    }
}
