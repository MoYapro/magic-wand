package de.moyapro.colors.wand.actions

import de.moyapro.colors.*
import de.moyapro.colors.game.actions.fight.*
import de.moyapro.colors.game.enemy.actions.*
import de.moyapro.colors.game.model.*
import io.kotest.matchers.*
import io.kotest.matchers.equality.*
import org.junit.*

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
