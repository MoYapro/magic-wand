package de.moyapro.colors.game.actions.fight

import de.moyapro.colors.game.*
import de.moyapro.colors.game.model.gameState.*
import io.kotest.assertions.throwables.*
import io.kotest.matchers.*
import io.kotest.matchers.equals.*
import org.junit.*

class StartFightActionTest {
    @Test
    fun `copy data from currentRun to current fight`() {
        val state = getExampleGameState().copy(currentFight = notStartedFight())
        val stateWithFightData = StartFightAction().apply(state).getOrThrow()
        stateWithFightData.currentFight.mages shouldBeEqual state.currentRun.mages
        stateWithFightData.currentFight.wands shouldBeEqual state.currentRun.activeWands
        stateWithFightData.currentFight.currentTurn shouldBe 1
        stateWithFightData.currentFight.magicToPlay shouldBe emptyList()
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