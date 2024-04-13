package de.moyapro.colors.game.actions

import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.takeTwo.Wand

data class RemoveWandAction(val wand: Wand) : GameAction("Remove wand from mage") {
    override val randomSeed: Int = -1

    override fun apply(oldState: MyGameState): Result<MyGameState> {
        check(oldState.wands.contains(wand)) { "Could not find wand to remove" }
        val updatedWands = oldState.wands.filter { it.id != wand.id }
        check(oldState.wands.size - updatedWands.size == 1) {"Did not remove exactly one wand"}
        return Result.success(oldState.copy(wands = updatedWands))
    }

}
