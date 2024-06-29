package de.moyapro.colors.game.actions

import de.moyapro.colors.game.*
import de.moyapro.colors.game.model.gameState.*

data class CombinedAction(val actions: List<GameAction>) : GameAction("Combined") {

    constructor(vararg actions: GameAction?) : this(actions.toList().filterNotNull())

    override val randomSeed: Int = this.hashCode()

    override fun apply(oldState: NewGameState): Result<NewGameState> {
        return actions.fold(Result.success(oldState), ::applyAllActions)
    }

    private fun applyAllActions(
        state: Result<NewGameState>,
        action: GameAction,
    ): Result<NewGameState> {
        return state.flatMap { action.apply(it) }
    }

}
