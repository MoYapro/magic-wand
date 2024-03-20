package de.moyapro.colors.game.actions

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import de.moyapro.colors.game.Enemy
import de.moyapro.colors.takeTwo.EnemyId

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = ZapAction::class, name = "ZapAction"),
)
interface RequiresTargetAction {

    val target: EnemyId?
    fun isValidTarget(enemy: Enemy): Boolean
    fun withSelection(target: Enemy): GameAction
}
