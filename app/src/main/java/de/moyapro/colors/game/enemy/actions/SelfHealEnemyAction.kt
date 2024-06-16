package de.moyapro.colors.game.enemy.actions

import com.fasterxml.jackson.annotation.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.enemy.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.gameState.*
import de.moyapro.colors.util.*

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = SelfHealEnemyAction::class, name = "SelfHealEnemyAction"),
)
data class SelfHealEnemyAction(override val name: String = "Self Heal") : EnemyAction<EnemyId> {
    override val randomSeed = this.hashCode()

    override fun init(self: EnemyId, gameState: NewGameState): GameAction {
        return SelfHealAction(self)
    }
}

data class SelfHealAction(val self: EnemyId) : GameAction("Self Heal") {
    override val randomSeed = this.hashCode()

    override fun apply(oldState: MyGameState): Result<MyGameState> {
        val target: Enemy = oldState.enemies.find { enemy -> enemy.id == self }
            ?: throw IllegalStateException("Could not find self: $self")
        val updatedEnemy = target.copy(health = target.health + 3)
        return Result.success(
            oldState.copy(
                enemies = oldState.enemies.replace(self, updatedEnemy)
            )
        )
    }

}
