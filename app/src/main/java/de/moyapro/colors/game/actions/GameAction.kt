package de.moyapro.colors.game.actions

import com.fasterxml.jackson.annotation.*
import de.moyapro.colors.game.actions.fight.*
import de.moyapro.colors.game.actions.loot.*
import de.moyapro.colors.game.enemy.*
import de.moyapro.colors.game.enemy.actions.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.gameState.*

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type")
@JsonSubTypes(
    JsonSubTypes.Type(value = PlaceMagicAction::class, name = "PlaceMagicAction"),
    JsonSubTypes.Type(value = EndTurnAction::class, name = "EndTurnAction"),
    JsonSubTypes.Type(value = AddWandAction::class, name = "AddWandAction"),
    JsonSubTypes.Type(value = ZapAction::class, name = "ZapAction"),
    JsonSubTypes.Type(value = NoOp::class, name = "NoOp"),
    JsonSubTypes.Type(value = EndTurnAction::class, name = "EndTurnAction"),
    JsonSubTypes.Type(value = TargetSelectedAction::class, name = "TargetSelectedAction"),
    JsonSubTypes.Type(value = ShowTargetSelectionAction::class, name = "ShowTargetSelectionAction"),
    JsonSubTypes.Type(value = HitMageAction::class, name = "HitMageAction"),
    JsonSubTypes.Type(value = SelfHealAction::class, name = "SelfHealAction"),

    )
abstract class GameAction(
    val name: String,
) {
    abstract val randomSeed: Int

    @JsonProperty("@type")
    private val type = this.javaClass.simpleName
    abstract fun apply(oldState: NewGameState): Result<NewGameState>
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
