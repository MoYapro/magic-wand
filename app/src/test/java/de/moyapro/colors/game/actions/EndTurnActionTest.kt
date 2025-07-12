package de.moyapro.colors.game.actions

import android.util.Log
import de.moyapro.colors.game.actions.fight.EndTurnAction
import de.moyapro.colors.game.actions.stash.AddGeneratorAction
import de.moyapro.colors.game.effect.Effect
import de.moyapro.colors.game.model.Magic
import de.moyapro.colors.game.model.MagicGenerator
import de.moyapro.colors.game.model.MagicType.*
import de.moyapro.colors.game.model.gameState.BattleBoard
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
    fun `End turn applies poison damage`() {
        val state = getExampleGameState()
        val updatedBattleBoard: BattleBoard = state.currentFight.battleBoard.mapEnemies { it.copy(statusEffects = mapOf(Effect.POISONED to 1)) }
        val stateWithPoison = state.updateCurrentFight(battleBoard = updatedBattleBoard)
        val startHealth = stateWithPoison.currentFight.battleBoard.fields[6].enemy?.health
        startHealth shouldBe 5
        val stateAfterTurn = EndTurnAction().apply(stateWithPoison).getOrThrow()
        val endHealth = stateAfterTurn.currentFight.battleBoard.fields[6].enemy?.health
        endHealth shouldBe 4
    }

    @Test
    fun `End turn makes poison fade`() {
        val state = getExampleGameState()
        val startPoison = 2
        val startWet = 2
        val updatedBattleBoard: BattleBoard = state.currentFight.battleBoard.mapEnemies { it.copy(statusEffects = mapOf(Effect.POISONED to startPoison, Effect.WET to startWet)) }
        val stateWithPoison = state.updateCurrentFight(battleBoard = updatedBattleBoard)
        val stateAfterTurn = EndTurnAction().apply(stateWithPoison).getOrThrow()
        val poisonAfter = stateAfterTurn.currentFight.battleBoard.fields[6].enemy?.statusEffects[Effect.POISONED] ?: 0
        val wetAfter = stateAfterTurn.currentFight.battleBoard.fields[6].enemy?.statusEffects[Effect.WET] ?: 0
        poisonAfter shouldBe startPoison - 1
        wetAfter shouldBe startWet - 1
    }

    @Test
    fun `End turn applies fire damage`() {
        val state = getExampleGameState()
        val updatedBattleBoard: BattleBoard = state.currentFight.battleBoard.mapEnemies { it.copy(statusEffects = mapOf(Effect.BURNING to 1)) }
        val stateWithPoison = state.updateCurrentFight(battleBoard = updatedBattleBoard)
        val startHealth = stateWithPoison.currentFight.battleBoard.fields[6].enemy?.health
        startHealth shouldBe 5
        val stateAfterTurn = EndTurnAction().apply(stateWithPoison).getOrThrow()
        val endHealth = stateAfterTurn.currentFight.battleBoard.fields[6].enemy?.health
        endHealth shouldBe 4
    }

    @Test
    fun `End turn makes fire grow`() {
        val state = getExampleGameState()
        val startBurning = 2
        val updatedBattleBoard: BattleBoard = state.currentFight.battleBoard.mapEnemies { it.copy(statusEffects = mapOf(Effect.BURNING to startBurning)) }
        val stateWithPoison = state.updateCurrentFight(battleBoard = updatedBattleBoard)
        val stateAfterTurn = EndTurnAction().apply(stateWithPoison).getOrThrow()
        val burningAfter = stateAfterTurn.currentFight.battleBoard.fields[6].enemy?.statusEffects[Effect.BURNING] ?: 0
        burningAfter shouldBe startBurning + 1
    }

    @Test
    fun `Generators create correct magic type`() {
        val state = getExampleGameState().updateCurrentFight(generators = listOf(MagicGenerator(GREEN, 1..1)), magicToPlay = emptyList())
        val stateAfterTurn = EndTurnAction().apply(state).getOrThrow()
        stateAfterTurn.currentFight.magicToPlay.map(Magic::type) shouldBe listOf(GREEN)
    }
}
