package de.moyapro.colors.game.actions

import de.moyapro.colors.game.Enemy
import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.takeTwo.EnemyId
import de.moyapro.colors.util.replace

interface EnemyAction<TARGET> {
    val name: String
    fun init(self: EnemyId, gameState: MyGameState): GameAction

}

class SelfHealEnemyAction() : EnemyAction<EnemyId> {
    override val name: String = "Self Heal"

    override fun init(self: EnemyId, gameState: MyGameState): GameAction {
        return SelfHealAction(self)
    }

}

private class SelfHealAction(val self: EnemyId) : GameAction {
    override val name: String = "Self Heal"

    override fun apply(oldState: MyGameState): Result<MyGameState> {
        val target: Enemy = oldState.enemies.find { enemy -> enemy.id == self }
            ?: throw IllegalStateException("Could not find self: $self")
        val updatedEnemy = target.copy(health = target.health + 1)
        return Result.success(
            oldState.copy(
                enemies = oldState.enemies.replace(self, updatedEnemy)
            )
        )
    }

}
