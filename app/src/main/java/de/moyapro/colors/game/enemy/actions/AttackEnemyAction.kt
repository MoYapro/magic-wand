package de.moyapro.colors.game.enemy.actions

import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.takeTwo.*

data class AttackEnemyAction(override val name: String = "Attack") : EnemyAction<EnemyId> {

    override fun init(self: EnemyId, gameState: MyGameState): GameAction {
        return AttackMageAction(MageId(1))
    }
}

data class AttackMageAction(val mageId: MageId) : GameAction("Attack player action") {
    override val randomSeed = this.hashCode()

    override fun apply(oldState: MyGameState): Result<MyGameState> {
//        val target: Enemy = oldState.enemies.find { enemy -> enemy.id == self }
//            ?: throw IllegalStateException("Could not find self: $self")
//        val updatedEnemy = target.copy(health = target.health + 3)
//        return Result.success(
//            oldState.copy(
//                enemies = oldState.enemies.replace(self, updatedEnemy)
//            )
//        )
        return Result.success(oldState)
    }

}
