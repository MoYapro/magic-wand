package de.moyapro.colors.game.actions.fight

import de.moyapro.colors.game.*
import de.moyapro.colors.util.*
import io.kotest.matchers.*
import org.junit.*

class EndFightActionTest {
    @Test
    fun `reset fight state`() {
        val state = getExampleGameState()
        val stateWithoutFightData = EndFightAction().apply(state).getOrThrow()
        stateWithoutFightData.currentFight.fightState shouldNotBe FightState.ONGOING
    }

}