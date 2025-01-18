package de.moyapro.colors.game.actions.fight

import de.moyapro.colors.util.FightState
import de.moyapro.colors.wand.getExampleGameState
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