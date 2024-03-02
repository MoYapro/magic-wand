package de.moyapro.colors.game

import de.moyapro.colors.takeTwo.Wand

data class AddWandAction(val wand: Wand) : GameAction {

    companion object {
        const val MAX_WANDS = 2
    }

    override fun apply(oldState: MyGameState): Result<MyGameState> {
        if (oldState.wands.size >= MAX_WANDS) return Result.failure(IllegalStateException("There are only $MAX_WANDS allowed"))
        return Result.success(MyGameState(oldState.wands + wand, oldState.magicToPlay))
    }

}
