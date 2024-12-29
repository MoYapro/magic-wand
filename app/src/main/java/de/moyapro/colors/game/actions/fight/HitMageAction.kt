package de.moyapro.colors.game.actions.fight

import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.enemy.actions.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.accessor.*
import de.moyapro.colors.game.model.gameState.*
import kotlin.random.*

data class HitMageAction(val targetMageId: MageId, val damage: Int, override val randomSeed: Int = Random.nextInt(), override val name: String = "HitMageAction") : EnemyAction<Mage> {


    fun apply(oldState: GameState): Result<GameState> {
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
    override fun init(self: EnemyId, gameState: GameState): GameAction {
        TODO("Not yet implemented")
    }
}