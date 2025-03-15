package de.moyapro.colors.game.actions

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import de.moyapro.colors.game.actions.fight.EndFightAction
import de.moyapro.colors.game.actions.fight.EndTurnAction
import de.moyapro.colors.game.actions.fight.LoseFightAction
import de.moyapro.colors.game.actions.fight.PlaceMagicAction
import de.moyapro.colors.game.actions.fight.ShowTargetSelectionAction
import de.moyapro.colors.game.actions.fight.StartFightAction
import de.moyapro.colors.game.actions.fight.TargetSelectedAction
import de.moyapro.colors.game.actions.fight.WinFightAction
import de.moyapro.colors.game.actions.fight.ZapAction
import de.moyapro.colors.game.actions.loot.ClaimLootAction
import de.moyapro.colors.game.actions.stash.AddGeneratorAction
import de.moyapro.colors.game.actions.stash.AddWandAction
import de.moyapro.colors.game.actions.stash.PlaceSpellAction
import de.moyapro.colors.game.actions.stash.RemoveSpellFromWandAction
import de.moyapro.colors.game.enemy.actions.AttackMageAction
import de.moyapro.colors.game.enemy.actions.SelfHealAction
import de.moyapro.colors.game.model.FieldId
import de.moyapro.colors.game.model.gameState.BattleBoard
import de.moyapro.colors.game.model.gameState.GameState

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
    JsonSubTypes.Type(value = AddGeneratorAction::class, name = "AddGeneratorAction"),
    JsonSubTypes.Type(value = IncreaseActionCounterAction::class, name = "IncreaseActionCounterAction"),
    JsonSubTypes.Type(value = StartFightAction::class, name = "StartFightAction"),
    JsonSubTypes.Type(value = EndFightAction::class, name = "EndFightAction"),
    JsonSubTypes.Type(value = EndFightAction::class, name = "EndFightAction"),
    JsonSubTypes.Type(value = WinFightAction::class, name = "WinFightAction"),
    JsonSubTypes.Type(value = LoseFightAction::class, name = "LoseFightAction"),
    JsonSubTypes.Type(value = CombinedAction::class, name = "CombinedAction"),
    JsonSubTypes.Type(value = RemoveSpellFromWandAction::class, name = "RemoveSpellFromWandAction"),
    JsonSubTypes.Type(value = PlaceSpellAction::class, name = "PlaceSpellAction"),
    JsonSubTypes.Type(value = SelfHealAction::class, name = "SelfHealAction"),
    JsonSubTypes.Type(value = AttackMageAction::class, name = "AttackMageAction"),
    JsonSubTypes.Type(value = ClaimLootAction::class, name = "ClaimLootAction"),
)
abstract class GameAction(
    val name: String,
) {
    abstract val randomSeed: Int

    @JsonProperty("@type")
    private val type = this.javaClass.simpleName
    abstract fun apply(oldState: GameState): Result<GameState>
    open val target: FieldId? = null
    open fun isValidTarget(battleBoard: BattleBoard, id: FieldId): Boolean = false
    open fun withSelection(targetFieldId: FieldId): GameAction = TODO("Overwrite me")
    open fun requireTargetSelection(): Boolean = false
    open fun onAddAction(actions: MutableList<GameAction>) {
        removeTargetAction(actions)
        actions.add(IncreaseActionCounterAction())
        actions.add(this)
    }

    private fun removeTargetAction(actions: MutableList<GameAction>) {
        if (actions.isNotEmpty() && actions.last() is ShowTargetSelectionAction) {
            actions.removeLast()
        }
    }

}
