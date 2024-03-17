package de.moyapro.colors.game.actions

import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.takeTwo.MageId

data class HitAction(val target: MageId) : GameAction("Hit Action") {

    override val randomSeed: Int = this.hashCode()

    override fun apply(oldState: MyGameState): Result<MyGameState> {
        val mageToHit = oldState.mage
            ?: throw IllegalStateException("Could not find mage")// TODO mage it a list
        val updatedMage = mageToHit.copy(health = mageToHit.health - 1)
        return Result.success(oldState.copy(mage = updatedMage))
    }
}