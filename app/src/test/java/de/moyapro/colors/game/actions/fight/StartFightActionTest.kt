package de.moyapro.colors.game.actions.fight

import android.util.Log
import de.moyapro.colors.game.model.gameState.notStartedFight
import de.moyapro.colors.wand.getExampleGameState
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.BeforeClass
import org.junit.Test

class StartFightActionTest {

    companion object {
        @BeforeClass
        @JvmStatic
        fun setup() {
            mockkStatic(Log::class)
            every { Log.d(any(), any()) } returns 1
            every { Log.e(any(), any()) } returns 1
            every { Log.i(any(), any()) } returns 1
        }
    }

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