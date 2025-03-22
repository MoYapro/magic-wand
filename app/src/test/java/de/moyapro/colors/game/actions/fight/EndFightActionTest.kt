package de.moyapro.colors.game.actions.fight

import de.moyapro.colors.util.FightState
import de.moyapro.colors.wand.getExampleGameState
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.Test

class EndFightActionTest {
    @Test
    fun `reset fight state`() {
        val state = getExampleGameState()
        val stateWithoutFightData = EndFightAction().apply(state).getOrThrow()
        stateWithoutFightData.currentFight.fightState shouldNotBe FightState.ONGOING
        stateWithoutFightData.currentFight.currentTurn shouldBe 0
    }

}