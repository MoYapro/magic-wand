package de.moyapro.colors.game.actions.fight

import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.takeTwo.*
import de.moyapro.colors.util.*

data class HitAction(val targetMageId: MageId, val damage: Int) : GameAction("Hit Action") {

    override val randomSeed: Int = this.hashCode()

    override fun apply(oldState: MyGameState): Result<MyGameState> {
        val mageToHit = oldState.mages.firstOrNull { mage -> mage.id == targetMageId }
            ?: throw IllegalStateException("Could not find mage")
        val updatedMage = mageToHit.copy(health = calculateNewHealth(mageToHit))
        val updatedMageList = oldState.mages.replace(targetMageId, updatedMage)
        return Result.success(oldState.copy(mages = updatedMageList))
    }

    private fun calculateNewHealth(mageToHit: Mage) = maxOf(0, mageToHit.health - damage)
}