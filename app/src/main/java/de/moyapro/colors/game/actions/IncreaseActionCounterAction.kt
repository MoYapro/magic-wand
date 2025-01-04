package de.moyapro.colors.game.actions

import de.moyapro.colors.game.model.gameState.GameState

data class IncreaseActionCounterAction(override val randomSeed: Int = -1) : GameAction("Increase action counter") {

    override fun apply(oldState: GameState): Result<GameState> {

        return Result.success(
            oldState.updateCurrentFight(
                currentTurn = oldState.currentFight.currentTurn + 1,
            )
        )
    }
}
