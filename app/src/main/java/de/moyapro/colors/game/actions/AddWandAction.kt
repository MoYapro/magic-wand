package de.moyapro.colors.game.actions

import de.moyapro.colors.game.*
import de.moyapro.colors.takeTwo.*
import de.moyapro.colors.util.*

data class AddWandAction(

    need mageId or have slots in use numbered to determin if replace or add in new position

val wandToAdd: Wand) : GameAction("Add Wand")
{

    override val randomSeed = this.hashCode()

    companion object {
        const val MAX_WANDS = 3
    }

    override fun apply(oldState: MyGameState): Result<MyGameState> {
        if (oldState.wands.size >= MAX_WANDS) return Result.failure(IllegalStateException("There are only $MAX_WANDS allowed"))
        check(oldState.wands.none { it.id == wandToAdd.id }) { "Cannot add wand - id is already there" }
        val mageWithoutWand: Mage? = findMageWithoutWand(oldState)
        check(mageWithoutWand != null) { "Could not add wand because there is no mage free to hold it" }
        val updatedMage = mageWithoutWand.copy(wandId = wandToAdd.id)
        return Result.success(
            oldState.copy(
                wands = oldState.wands + wandToAdd,
                mages = oldState.mages.replace(updatedMage)
            )
        )
    }

    private fun findMageWithoutWand(state: MyGameState): Mage? {
        return state.mages.firstOrNull { it.wandId == null }
    }

}
