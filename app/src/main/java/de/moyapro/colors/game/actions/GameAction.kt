package de.moyapro.colors.game.actions

import com.fasterxml.jackson.annotation.*
import de.moyapro.colors.game.*
import de.moyapro.colors.takeTwo.*

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
    JsonSubTypes.Type(value = GiveWandAction::class, name = "GiveWandAction"),
    JsonSubTypes.Type(value = SelfHealAction::class, name = "SelfHealAction"),

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
        removeTargetAction(actions)
        actions.add(IncreaseActionCounterAction)
        actions.add(this)
    }

    private fun removeTargetAction(actions: MutableList<GameAction>) {
        if (actions.isNotEmpty() && actions.last() is ShowTargetSelectionAction) {
            actions.removeLast()
        }
    }

}
