package de.moyapro.colors.game.actions

import de.moyapro.colors.*
import de.moyapro.colors.game.actions.fight.*
import de.moyapro.colors.game.model.*
import io.kotest.matchers.equality.*
import org.junit.*

class EndTurnActionTest {
    @Test
    fun `EndTurn should yield the same results`() {
        val state = NewGameState(
            enemies = listOf(createExampleEnemy()),
            wands = listOf(createExampleWand()),
            magicToPlay = listOf(createExampleMagic()),
            currentTurn = 0,
        )
        val endTurnAction = EndTurnAction(1)
        val nextTurnStates = (0..10).map { endTurnAction.apply(state).getOrThrow() }
        nextTurnStates.forEach { nextTurnStates.first() shouldBeEqualToComparingFields it }
    }
}
