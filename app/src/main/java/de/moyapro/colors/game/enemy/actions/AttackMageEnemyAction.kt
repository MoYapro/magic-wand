package de.moyapro.colors.game.enemy.actions

import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.actions.NoOp
import de.moyapro.colors.game.enemy.Enemy
import de.moyapro.colors.game.model.EnemyId
import de.moyapro.colors.game.model.Mage
import de.moyapro.colors.game.model.MageId
import de.moyapro.colors.game.model.accessor.findById
import de.moyapro.colors.game.model.accessor.findMage
import de.moyapro.colors.game.model.gameState.GameState
import de.moyapro.colors.util.replace
import kotlin.random.Random

data class AttackMageEnemyAction(override val name: String = "Attack") : EnemyAction<MageId> {
    override val randomSeed = this.hashCode()

    override fun init(self: EnemyId, gameState: GameState): GameAction {
        val random = Random(self.id.hashCode())

        val target = selectTarget(gameState, random)
        if (target == null) return NoOp()
        return AttackMageAction(target.id, self)
    }

    private fun selectTarget(gameState: GameState, random: Random): Mage? {
        val aliveMage = gameState.currentFight.mages.filter { it.health > 0 }
        if (aliveMage.isEmpty()) {
            return null
        }
        return aliveMage.random(random)
    }
}

data class AttackMageAction(val mageId: MageId, val attackerId: EnemyId) : GameAction("Attack ${symbolForMageId(mageId)}") {
    override val randomSeed = this.mageId.hashCode()

    override fun apply(oldState: GameState): Result<GameState> {
        val mage = oldState.currentFight.findMage(mageId)
        val attacker: Enemy? = oldState.currentFight.battleBoard.getEnemies().findById(attackerId)
        if (attacker == null || attacker.health <= 0) return Result.success(oldState)
        val updatedMage = mage.copy(health = mage.health - attacker.power)
        return Result.success(
            oldState.updateCurrentFight(
                mages = oldState.currentFight.mages.replace(updatedMage)
            )
        )
    }


}

fun symbolForMageId(mageId: MageId): String {
    return when (mageId.id.toInt()) {
        0 -> "<"
        1 -> "v"
        2 -> ">"
        else -> throw IllegalArgumentException("MageId must be 0,1,2 but this was: $mageId")
    }
}

