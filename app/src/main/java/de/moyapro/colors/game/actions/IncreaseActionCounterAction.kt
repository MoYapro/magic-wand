package de.moyapro.colors.game.actions

import de.moyapro.colors.game.model.*

object IncreaseActionCounterAction : GameAction("Increase action counter") {
    override val randomSeed: Int = -1

    override fun apply(oldState: MyGameState): Result<MyGameState> {
        return Result.success(oldState.copy(actionCounter = oldState.actionCounter + 1))
    }
}
