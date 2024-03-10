package de.moyapro.colors.game.actions

import de.moyapro.colors.game.MyGameState
import kotlin.random.Random

class NoOp : GameAction("NoOp") {
    override val randomSeed = this.hashCode()

    override fun apply(oldState: MyGameState): Result<MyGameState> = Result.success(oldState)
}
