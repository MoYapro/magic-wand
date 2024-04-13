package de.moyapro.colors.game.actions

import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.takeTwo.Wand

data class AddWandAction(val wand: Wand) : GameAction("Add Wand") {

    override val randomSeed = this.hashCode()

    companion object {
        const val MAX_WANDS = 3
    }

    override fun apply(oldState: MyGameState): Result<MyGameState> {
        if (oldState.wands.size >= MAX_WANDS) return Result.failure(IllegalStateException("There are only $MAX_WANDS allowed"))
        check(oldState.wands.none { it.id == wand.id }) { "Cannot add wand - id is already there" }
        return Result.success(
            oldState.copy(
                wands = oldState.wands + wand,
            )
        )
    }

}
