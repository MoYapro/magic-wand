package de.moyapro.colors.game.actions.fight

import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.model.gameState.GameState

data class ShowTargetSelectionAction(val originalAction: GameAction) :
    GameAction("Show target selection") {
    override val randomSeed: Int = this.hashCode()

    override fun apply(oldState: GameState): Result<GameState> {
        val updatedFields = oldState.currentFight.battleBoard.fields
            .map { field ->
                if (originalAction.isValidTarget(oldState.currentFight.battleBoard, field.id)) field.copy(showTarget = true)
                else field
            }
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
