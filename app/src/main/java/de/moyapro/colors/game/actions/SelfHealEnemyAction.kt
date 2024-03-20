package de.moyapro.colors.game.actions

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import de.moyapro.colors.game.Enemy
import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.takeTwo.EnemyId
import de.moyapro.colors.util.replace

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = SelfHealEnemyAction::class, name = "SelfHealEnemyAction"),
)
interface EnemyAction<TARGET> {
    val name: String
    fun init(self: EnemyId, gameState: MyGameState): GameAction
}

data class SelfHealEnemyAction(override val name: String = "Self Heal") : EnemyAction<EnemyId> {


    override fun init(self: EnemyId, gameState: MyGameState): GameAction {
        return SelfHealAction(self)
    }

}

private data class SelfHealAction(val self: EnemyId) : GameAction("Self Heal") {
    override val randomSeed = this.hashCode()

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
