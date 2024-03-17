package de.moyapro.colors.game.actions

import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.takeTwo.MageId
import de.moyapro.colors.util.replace

data class HitAction(val target: MageId) : GameAction("Hit Action") {

    override val randomSeed: Int = this.hashCode()

    override fun apply(oldState: MyGameState): Result<MyGameState> {
        val mageToHit = oldState.mages.firstOrNull { mage -> mage.id == target }
            ?: throw IllegalStateException("Could not find mage")// TODO mage it a list
        val updatedMage = mageToHit.copy(health = mageToHit.health - 1)
        val updatedMageList = oldState.mages.replace(target, updatedMage)
        return Result.success(oldState.copy(mages = updatedMageList))
    }
}