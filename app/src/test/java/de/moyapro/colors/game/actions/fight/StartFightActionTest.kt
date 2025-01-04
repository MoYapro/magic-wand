package de.moyapro.colors.game.actions.fight

import de.moyapro.colors.game.getExampleGameState
import de.moyapro.colors.game.model.gameState.notStartedFight
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
import org.junit.Test

class StartFightActionTest {
    @Test
    fun `copy data from currentRun to current fight`() {
        val state = getExampleGameState().copy(currentFight = notStartedFight())
        val stateWithFightData = StartFightAction().apply(state).getOrThrow()
        stateWithFightData.currentFight.mages shouldBeEqual state.currentRun.mages
        stateWithFightData.currentFight.wands shouldBeEqual state.currentRun.activeWands
        stateWithFightData.currentFight.currentTurn shouldBe 1
        stateWithFightData.currentFight.magicToPlay shouldBe emptyList()
        stateWithFightData.currentFight.generators shouldBe state.currentRun.generators
    }

    @Test
    fun `cannot start without 3 mages`() {
        val state = getExampleGameState().updateCurrentRun(mages = emptyList())
        shouldThrow<IllegalArgumentException> { StartFightAction().apply(state).getOrThrow() }
    }

    @Test
    fun `each mage must have a wand`() {
        val state = getExampleGameState().updateCurrentRun(activeWands = emptyList())
        shouldThrow<IllegalArgumentException> { StartFightAction().apply(state).getOrThrow() }
    }
}