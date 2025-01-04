package de.moyapro.colors.game.actions.fight

import de.moyapro.colors.game.getExampleGameState
import de.moyapro.colors.util.FightState
import io.kotest.matchers.shouldBe
import org.junit.Test

class WinFightActionTest {
    @Test
    fun `win fight`() {
        val state = getExampleGameState()
        val winState = WinFightAction().apply(state).getOrThrow()
        winState.currentFight.fightState shouldBe FightState.WIN
    }

}