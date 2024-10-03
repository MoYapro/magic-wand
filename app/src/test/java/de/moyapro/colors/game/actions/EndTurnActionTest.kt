package de.moyapro.colors.game.actions

import android.util.*
import de.moyapro.colors.game.actions.fight.*
import de.moyapro.colors.game.actions.loot.*
import de.moyapro.colors.game.generators.*
import io.kotest.matchers.equality.*
import io.kotest.matchers.ints.*
import io.mockk.*
import org.junit.*

class EndTurnActionTest {

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
    fun `EndTurn should yield the same results`() {
        val state = StartFightFactory.setupFightStage()
        val endTurnAction = EndTurnAction(1)
        val nextTurnStates = (0..10).map { endTurnAction.apply(state).getOrThrow() }
        nextTurnStates.forEach { nextTurnStates.first() shouldBeEqualToComparingFields it }
    }

    @Test
    fun `EndTurn generates Magic`() {
        val state = StartFightFactory.setupFightStage()
        val stateAfter = EndTurnAction().apply(state).getOrThrow()
        state.currentFight.magicToPlay.size shouldBeLessThan stateAfter.currentFight.magicToPlay.size
    }

    @Test
    fun `More generators create more magic`() {
        val stateWithGenerator = AddGeneratorAction().apply(StartFightFactory.setupFightStage()).getOrThrow()
        val stateAfterTurn = EndTurnAction().apply(stateWithGenerator).getOrThrow()
        stateWithGenerator.currentFight.magicToPlay.size shouldBeLessThan stateAfterTurn.currentFight.magicToPlay.size
    }
}
