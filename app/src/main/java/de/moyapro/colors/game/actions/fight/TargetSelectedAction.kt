package de.moyapro.colors.game.actions.fight

import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.model.FieldId
import de.moyapro.colors.game.model.gameState.GameState

data class TargetSelectedAction(val targetFieldId: FieldId) :
    GameAction("Target selected action") {
    override val randomSeed: Int = this.hashCode()

    override fun onAddAction(actions: MutableList<GameAction>) {
        check(actions.last() is ShowTargetSelectionAction) { "TargetSelectedAction has not showTargetSelectionAction predecessor" }
        val showTargetSelectionAction = actions.removeLast() as ShowTargetSelectionAction
        actions.add(showTargetSelectionAction.originalAction.withSelection(targetFieldId))
    }

    override fun apply(oldState: GameState): Result<GameState> {
        val updatedFields = oldState.currentFight.battleBoard.fields.map { field -> field.copy(showTarget = false) }
        val updatedBattleBoard = oldState.currentFight.battleBoard.copy(fields = updatedFields)
        return Result.success(
            oldState.copy(
                currentFight = oldState.currentFight.copy(
                    battleBoard = updatedBattleBoard
                )
            )
        )

    }
}
