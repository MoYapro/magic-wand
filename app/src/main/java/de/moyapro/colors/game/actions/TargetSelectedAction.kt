package de.moyapro.colors.game.actions

import de.moyapro.colors.game.Enemy
import de.moyapro.colors.game.MyGameState

data class TargetSelectedAction(val target: Enemy) : GameAction("Target selected action") {
    override val randomSeed: Int = this.hashCode()

    override fun apply(oldState: MyGameState): Result<MyGameState> {
        return Result.success(oldState)
    }
}
