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
        check(wandToUpdate != null)
        check(mageToUpdate != null)
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