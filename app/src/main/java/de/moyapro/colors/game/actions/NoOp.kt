package de.moyapro.colors.game.actions

import de.moyapro.colors.game.model.*

class NoOp : GameAction("NoOp") {
    override val randomSeed = -1

    override fun apply(oldState: MyGameState): Result<MyGameState> = Result.success(oldState)
}
