package de.moyapro.colors.game.actions

import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.takeTwo.Wand

data class RemoveWandAction(val wand: Wand) : GameAction("Remove wand from loot") {
    override val randomSeed: Int = -1

    override fun apply(oldState: MyGameState): Result<MyGameState> {
        check(oldState.wands.contains(wand)) { "Could not find wand to remove" }
        return Result.success(oldState.copy(wands = oldState.loot.wands.filter { it.id != wand.id }))
    }

}
