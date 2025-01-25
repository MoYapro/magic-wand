package de.moyapro.colors.game.enemy.actions

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.model.EnemyId
import de.moyapro.colors.game.model.gameState.GameState

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = SelfHealEnemyAction::class, name = "SelfHealEnemyAction"),
)
data class SelfHealEnemyAction(override val name: String = "Self Heal") : EnemyAction<EnemyId> {
    override val randomSeed = this.hashCode()

    override fun init(self: EnemyId, gameState: GameState): GameAction {
        return SelfHealAction(self)
    }
}

data class SelfHealAction(val self: EnemyId) : GameAction("Self Heal") {
    override val randomSeed = this.hashCode()

    override fun apply(oldState: GameState): Result<GameState> {
        val currentFight = oldState.currentFight
        val updatedBattleBoard = currentFight.battleBoard.mapEnemies { enemy -> enemy.copy(health = enemy.health + 3) }
        return Result.success(
            oldState.updateCurrentFight(battlefield = updatedBattleBoard)
        )
    }

}
