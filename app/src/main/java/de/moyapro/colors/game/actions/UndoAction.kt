package de.moyapro.colors.game.actions

import de.moyapro.colors.game.MyGameState

object UndoAction : GameAction("Undo") {
    override val randomSeed: Int = 0

    override fun apply(oldState: MyGameState): Result<MyGameState> {
        return Result.success(oldState)
    }
}