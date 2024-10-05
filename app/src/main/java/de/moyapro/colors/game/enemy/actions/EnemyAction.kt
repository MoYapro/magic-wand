package de.moyapro.colors.game.enemy.actions

import com.fasterxml.jackson.annotation.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.gameState.*

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = SelfHealEnemyAction::class, name = "SelfHealEnemyAction"),
)
interface EnemyAction<TARGET> {
    val randomSeed: Int
    val name: String
    fun init(self: EnemyId, gameState: GameState): GameAction
}