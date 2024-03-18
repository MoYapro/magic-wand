package de.moyapro.colors.game.actions

import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.takeTwo.MageId
import de.moyapro.colors.takeTwo.WandId
import de.moyapro.colors.util.replace

data class GiveWandAction(val wandId: WandId, val mageId: MageId) : GameAction("Give Wand") {
    override val randomSeed: Int = this.hashCode()

    override fun apply(oldState: MyGameState): Result<MyGameState> {
        val wandToUpdate = oldState.findWand(wandId)
        val mageToUpdate = oldState.findMage(mageId)
        check(wandToUpdate != null) { "Could not find wand to give to mage" }
        check(mageToUpdate != null) { "Could not find mage to give wand to" }
        if (mageToUpdate.wandId != null) return Result.failure(IllegalStateException("Mage does already have a wand"))
        val updatedWand = wandToUpdate.copy(mageId = mageId)
        val updatedMage = mageToUpdate.copy(wandId = wandId)
        return Result.success(
            oldState.copy(
                mages = oldState.mages.replace(mageId, updatedMage),
                wands = oldState.wands.replace(wandId, updatedWand)
            )
        )
    }
}