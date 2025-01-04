package de.moyapro.colors.game.enemy.actions

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.actions.fight.HitMageAction
import de.moyapro.colors.game.model.EnemyId
import de.moyapro.colors.game.model.gameState.GameState

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = SelfHealEnemyAction::class, name = "SelfHealEnemyAction"),
    JsonSubTypes.Type(value = HitMageAction::class, name = "HitMageAction"),
    JsonSubTypes.Type(value = AttackMageEnemyAction::class, name = "AttackMageEnemyAction"),
)
interface EnemyAction<TARGET> {
    val randomSeed: Int
    val name: String
    fun init(self: EnemyId, gameState: GameState): GameAction
}