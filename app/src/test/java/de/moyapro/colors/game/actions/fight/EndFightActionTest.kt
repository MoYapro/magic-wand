package de.moyapro.colors.game.actions.fight

import de.moyapro.colors.game.getExampleGameState
import de.moyapro.colors.util.FightState
import io.kotest.matchers.shouldNotBe
import org.junit.Test

class EndFightActionTest {
    @Test
    fun `reset fight state`() {
        val state = getExampleGameState()
        val stateWithoutFightData = EndFightAction().apply(state).getOrThrow()
        stateWithoutFightData.currentFight.fightState shouldNotBe FightState.ONGOING
    }

}