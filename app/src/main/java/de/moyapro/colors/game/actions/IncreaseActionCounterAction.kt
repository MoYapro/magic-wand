package de.moyapro.colors.game.actions

import de.moyapro.colors.game.model.gameState.*

object IncreaseActionCounterAction : GameAction("Increase action counter") {
    override val randomSeed: Int = -1

    override fun apply(oldState: GameState): Result<GameState> {

        return Result.success(
            oldState.updateCurrentFight(
                currentTurn = oldState.currentFight.currentTurn + 1,
            )
        )
    }
}
