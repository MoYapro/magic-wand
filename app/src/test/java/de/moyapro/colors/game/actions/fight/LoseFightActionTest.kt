package de.moyapro.colors.game.actions.fight

import de.moyapro.colors.util.FightState
import de.moyapro.colors.wand.getExampleGameState
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