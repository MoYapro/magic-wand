package de.moyapro.colors.game.enemy.actions

import com.fasterxml.jackson.annotation.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.gameState.*

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

    override fun apply(oldState: NewGameState): Result<NewGameState> {
        val currentFight = oldState.currentFight
        val updatedBattleBoard = currentFight.battleBoard.mapEnemies { enemy -> enemy.copy(health = enemy.health + 3) }
        return Result.success(
            oldState.updateCurrentFight(battlefield = updatedBattleBoard)
        )
    }

}
