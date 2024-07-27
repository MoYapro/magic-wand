package de.moyapro.colors.game.actions.fight

import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.enemy.*
import de.moyapro.colors.game.model.gameState.*
import io.kotest.matchers.*
import org.junit.*

internal class ShowTargetSelectionActionTest {

    @Test
    fun `should set targets on hitable enemies`() {
        val state = getExampleGameState()
        val updatedState = ShowTargetSelectionAction(
            ZapAction(
                target = state.currentFight.battleBoard.getEnemies().first().id,
                wandId = state.currentFight.wands.first().id
            )
        ).apply(state).getOrThrow()
        updatedState.currentFight.battleBoard.getEnemies().all { enemy -> enemy.showTarget } shouldBe true
    }

    @Test
    fun `should not select targets when they are not hittable`() {
        val state = getExampleGameState()
        val updatedState = ShowTargetSelectionAction(
            targetNothingAction
        ).apply(state).getOrThrow()
        updatedState.currentFight.battleBoard.getEnemies().all { enemy -> enemy.showTarget } shouldBe false
    }

    private val targetNothingAction = object : GameAction("Target nothing") {
        override val randomSeed = -99
        override fun apply(oldState: NewGameState) = Result.success(oldState)
        override fun isValidTarget(enemy: Enemy): Boolean {
            return false
        }
    }
}


