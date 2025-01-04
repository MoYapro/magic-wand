package de.moyapro.colors.game.actions.fight

import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.enemy.actions.EnemyAction
import de.moyapro.colors.game.model.EnemyId
import de.moyapro.colors.game.model.Mage
import de.moyapro.colors.game.model.MageId
import de.moyapro.colors.game.model.accessor.findMage
import de.moyapro.colors.game.model.accessor.updateMage
import de.moyapro.colors.game.model.gameState.GameState
import kotlin.random.Random

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