package de.moyapro.colors.game.actions

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import de.moyapro.colors.game.Enemy
import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.takeTwo.EnemyId

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = PlaceMagicAction::class, name = "PlaceMagicAction"),
    JsonSubTypes.Type(value = EndTurnAction::class, name = "EndTurnAction"),
    JsonSubTypes.Type(value = AddWandAction::class, name = "AddWandAction"),
    JsonSubTypes.Type(value = ZapAction::class, name = "ZapAction"),
    JsonSubTypes.Type(value = NoOp::class, name = "NoOp"),
    JsonSubTypes.Type(value = EndTurnAction::class, name = "EndTurnAction"),
    JsonSubTypes.Type(value = TargetSelectedAction::class, name = "TargetSelectedAction"),
    JsonSubTypes.Type(value = ShowTargetSelectionAction::class, name = "ShowTargetSelectionAction"),
    JsonSubTypes.Type(value = HitAction::class, name = "HitAction"),
    JsonSubTypes.Type(value = GiveWandAction::class, name = "GiveWandAction")
)
abstract class GameAction(
    val name: String,
) {
    abstract val randomSeed: Int
    abstract fun apply(oldState: MyGameState): Result<MyGameState>
    open val target: EnemyId? = null
    open fun isValidTarget(enemy: Enemy): Boolean = false
    open fun withSelection(targetId: EnemyId): GameAction = TODO("Overwrite me")
    open fun requireTargetSelection(): Boolean = false
    open fun onAddAction(actions: MutableList<GameAction>) {
        actions.add(this)
    }

}
