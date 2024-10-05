package de.moyapro.colors.game.actions.fight

import de.moyapro.colors.game.*
import de.moyapro.colors.util.*
import io.kotest.matchers.*
import org.junit.*

class WinFightActionTest {
    @Test
    fun `win fight`() {
        val state = getExampleGameState()
        val winState = WinFightAction().apply(state).getOrThrow()
        winState.currentFight.fightState shouldBe FightState.WIN
    }

}