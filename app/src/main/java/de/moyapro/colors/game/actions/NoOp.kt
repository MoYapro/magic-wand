package de.moyapro.colors.game.actions

import de.moyapro.colors.game.MyGameState

class NoOp : GameAction {
    override val name: String = "NoOp"

    override fun apply(oldState: MyGameState): Result<MyGameState> = Result.success(oldState)
}
