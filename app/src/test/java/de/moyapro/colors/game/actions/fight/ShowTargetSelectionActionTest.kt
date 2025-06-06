package de.moyapro.colors.game.actions.fight

import android.util.Log
import de.moyapro.colors.game.GameViewModel
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.functions.isInFrontRow
import de.moyapro.colors.game.generators.Initializer
import de.moyapro.colors.game.model.FieldId
import de.moyapro.colors.game.model.gameState.BattleBoard
import de.moyapro.colors.game.model.gameState.GameState
import de.moyapro.colors.wand.getExampleGameState
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.BeforeClass
import org.junit.Test

internal class ShowTargetSelectionActionTest {

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
    fun `should set targets on hitable fields`() {
        val state = getExampleGameState()
        val gameViewModel = GameViewModel(state, loadActions = { emptyList() }, saveState = { _, _ -> Unit }, saveActions = {}, loadState = { Initializer.createInitialGameState() })
        gameViewModel.addAction(
            ZapAction(
                target = state.currentFight.battleBoard.fields.first().id,
                wandId = state.currentFight.wands.first().id
            )
        )
        val currentGameState = gameViewModel.uiState.value.getOrThrow()
        currentGameState.currentFight.battleBoard.fields.filter { isInFrontRow(it.id) }.all { enemy -> enemy.showTarget } shouldBe true
        currentGameState.currentFight.battleBoard.fields.filter { !isInFrontRow(it.id) }.all { enemy -> enemy.showTarget } shouldBe false
    }

    @Test
    fun `should not select targets when they are not hittable`() {
        val state = getExampleGameState()
        val updatedState = ShowTargetSelectionAction(
            targetNothingAction
        ).apply(state).getOrThrow()
        updatedState.currentFight.battleBoard.fields.all { enemy -> enemy.showTarget } shouldBe false
    }

    private val targetNothingAction = object : GameAction("Target nothing") {
        override val randomSeed = -99
        override fun apply(oldState: GameState) = Result.success(oldState)
        override fun isValidTarget(battleBoard: BattleBoard, id: FieldId): Boolean {
            return false
        }
    }
}


