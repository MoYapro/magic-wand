package de.moyapro.colors.game.actions.fight

import de.moyapro.colors.game.getExampleGameState
import de.moyapro.colors.util.FightState
import io.kotest.matchers.shouldBe
import org.junit.Test

class LoseFightActionTest {
    @Test
    fun `lose fight`() {
        val state = getExampleGameState()
        val lostState = LoseFightAction().apply(state).getOrThrow()
        lostState.currentFight.fightState shouldBe FightState.LOST
    }

}