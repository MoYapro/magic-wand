package de.moyapro.colors.game.actions

import de.moyapro.colors.game.model.gameState.GameState

object UndoAction : GameAction("Undo") {
    override val randomSeed: Int = 0

    override fun onAddAction(actions: MutableList<GameAction>) {
        if (actions.isNotEmpty()) actions.removeLast()
    }

    override fun apply(oldState: GameState): Result<GameState> {
        return Result.success(oldState)
    }
}