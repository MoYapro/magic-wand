package de.moyapro.colors.game

class EndTurnAction() : GameAction {

    override fun apply(oldState: MyGameState): Result<MyGameState> {
        val foldingFunction = { state: Result<MyGameState>, action: GameAction ->
            state.flatMap { action.apply(it) }
        }
        val result = oldState.enemyActionsPerTurn[oldState.currentTurn].fold(
            Result.success(oldState),
            foldingFunction
        )
        return result.map(this::prepareNextTurn)
    }


    fun prepareNextTurn(gameState: MyGameState): MyGameState {
        val nextTurnEnemyActions = emptyList<GameAction>()
        val enemyActionsPerTurn: MutableList<List<GameAction>> =
            gameState.enemyActionsPerTurn.toMutableList()
        enemyActionsPerTurn.add(nextTurnEnemyActions)
        return gameState.copy(
            currentTurn = gameState.currentTurn + 1,
            enemyActionsPerTurn = enemyActionsPerTurn,
        )
    }
}
