package de.moyapro.colors.game.actions.fight

import de.moyapro.colors.game.*
import de.moyapro.colors.util.*
import io.kotest.matchers.*
import org.junit.*

class LoseFightActionTest {
    @Test
    fun `lose fight`() {
        val state = getExampleGameState()
        val lostState = LoseFightAction().apply(state).getOrThrow()
        lostState.currentFight.fightState shouldBe FightState.LOST
    }

}