package de.moyapro.colors.game.actions.fight

import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.accessor.*
import de.moyapro.colors.game.model.gameState.*

data class HitMageAction(val targetMageId: MageId, val damage: Int) : GameAction("Hit Action") {

    override val randomSeed: Int = this.hashCode()

    override fun apply(oldState: NewGameState): Result<NewGameState> {
        val mageToHit = oldState.currentFight.findMage(targetMageId)
        val updatedMage = mageToHit.copy(health = calculateNewHealth(mageToHit))
        val updatedMageList = oldState.currentFight.updateMage(updatedMage)
        return Result.success(
            oldState.copy(
                currentFight = oldState.currentFight.copy(
                    mages = updatedMageList
                )
            )
        )
    }

    private fun calculateNewHealth(mageToHit: Mage) = maxOf(0, mageToHit.health - damage)
}