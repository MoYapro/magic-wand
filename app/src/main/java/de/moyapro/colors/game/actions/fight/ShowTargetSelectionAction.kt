package de.moyapro.colors.game.actions.fight

import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.model.gameState.*

data class ShowTargetSelectionAction(val originalAction: GameAction) :
    GameAction("Show target selection") {
    override val randomSeed: Int = this.hashCode()

    override fun apply(oldState: NewGameState): Result<NewGameState> {
        val updatedBattleBoard = oldState.currentFight.battleBoard
            .mapEnemies { enemy ->
                if (originalAction.isValidTarget(enemy)) enemy.copy(showTarget = true)
                else enemy
            }
        return Result.success(
            oldState.copy(
                currentFight = oldState.currentFight.copy(
                    battleBoard = updatedBattleBoard
                )
            )
        )
    }
}
