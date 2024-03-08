package de.moyapro.colors.game.actions

import de.moyapro.colors.game.Enemy
import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.game.flatMap

class EndTurnAction() : GameAction {

    override val name: String = "End turn"
    override fun apply(oldState: MyGameState): Result<MyGameState> {
        val foldingFunction = { state: Result<MyGameState>, action: GameAction ->
            state.flatMap { action.apply(it) }
        }
        val result = oldState.enemies
            .map(Enemy::nextAction)
            .fold(Result.success(oldState), foldingFunction)
        return result.map(this::prepareNextTurn)
    }


    private fun prepareNextTurn(gameState: MyGameState): MyGameState {
        val nextTurnEnemiesWithActions = calculateEnemyTurn(gameState)
        return gameState.copy(
            currentTurn = gameState.currentTurn + 1,
            enemies = nextTurnEnemiesWithActions
        )
    }

    private fun calculateEnemyTurn(gameState: MyGameState): List<Enemy> {
        return gameState.enemies.map { enemy ->
            val nextAction = enemy.possibleActions.random()
            val nextGameAction = nextAction.init(enemy.id, gameState)
            enemy.copy(nextAction = nextGameAction)
        }
    }
}
