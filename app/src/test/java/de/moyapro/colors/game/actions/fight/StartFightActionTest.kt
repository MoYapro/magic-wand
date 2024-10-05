package de.moyapro.colors.game.actions.fight

import de.moyapro.colors.game.*
import io.kotest.matchers.*
import io.kotest.matchers.equals.*
import org.junit.*

class StartFightActionTest {
    @Test
    fun `copy data from currentRun to current fight`() {
        val state = getExampleGameState()
        val stateWithFightData = StartFightAction().apply(state).getOrThrow()
        stateWithFightData.currentFight.mages shouldBeEqual state.currentRun.mages
        stateWithFightData.currentFight.wands shouldBeEqual state.currentRun.activeWands
        stateWithFightData.currentFight.currentTurn shouldBe 1
        stateWithFightData.currentFight.magicToPlay shouldBe emptyList()
    }
}