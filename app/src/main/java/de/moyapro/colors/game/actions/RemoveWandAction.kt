package de.moyapro.colors.game.actions

import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.takeTwo.Wand
import de.moyapro.colors.util.replace

data class RemoveWandAction(val wandToRemove: Wand) : GameAction("Remove wand from mage") {
    override val randomSeed: Int = -1

    override fun apply(oldState: MyGameState): Result<MyGameState> {
        check(oldState.wands.contains(wandToRemove)) { "Could not find wand to remove" }
        check(oldState.findMage(wandToRemove.id) != null) { "Could not remove wand because no mage is holding it" }
        val updatedWands = oldState.wands.filter { it.id != wandToRemove.id }
        val updatedMage = oldState.findMage(wandToRemove.id)!!.copy(wandId = null)
        check(oldState.wands.size - updatedWands.size == 1) {"Did not remove exactly one wand"}
        return Result.success(
            oldState.copy(
                wands = updatedWands,
                mages = oldState.mages.replace(updatedMage)
            )
        )
    }

}
