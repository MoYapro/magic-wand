package de.moyapro.colors.game.actions

import android.util.Log
import de.moyapro.colors.game.actions.fight.EndTurnAction
import de.moyapro.colors.game.actions.loot.AddGeneratorAction
import de.moyapro.colors.game.model.Magic
import de.moyapro.colors.game.model.MagicGenerator
import de.moyapro.colors.game.model.MagicType.*
import de.moyapro.colors.wand.getExampleGameState
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.kotest.matchers.ints.shouldBeLessThan
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.BeforeClass
import org.junit.Test

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
        val state = getExampleGameState()
        val endTurnAction = EndTurnAction(99)
        val nextTurnStates = (0..10).map { endTurnAction.apply(state).getOrThrow() }
        nextTurnStates.forEachIndexed { _, bob -> nextTurnStates.first() shouldBeEqualToComparingFields bob }
    }

    @Test
    fun `EndTurn generates Magic`() {
        val state = getExampleGameState()
        val stateAfter = EndTurnAction().apply(state).getOrThrow()
        state.currentFight.magicToPlay.size shouldBeLessThan stateAfter.currentFight.magicToPlay.size
    }

    @Test
    fun `More generators create more magic`() {
        val state = getExampleGameState()
        val stateWithGenerator = AddGeneratorAction().apply(state).getOrThrow()
        val stateAfterTurn = EndTurnAction().apply(stateWithGenerator).getOrThrow()
        stateWithGenerator.currentFight.magicToPlay.size shouldBeLessThan stateAfterTurn.currentFight.magicToPlay.size
    }

    @Test
    fun `Generators create correct magic type`() {
        val state = getExampleGameState().updateCurrentFight(generators = listOf(MagicGenerator(GREEN, 1..1)), magicToPlay = emptyList())
        val stateAfterTurn = EndTurnAction().apply(state).getOrThrow()
        stateAfterTurn.currentFight.magicToPlay.map(Magic::type) shouldBe listOf(GREEN)
    }
}
