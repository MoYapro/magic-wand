package de.moyapro.colors.game.actions

import de.moyapro.colors.game.*

data class CombinedAction(val actions: List<GameAction>) : GameAction("Combined") {

    constructor(vararg actions: GameAction?) : this(actions.toList().filterNotNull())

    override val randomSeed: Int = this.hashCode()

    override fun apply(oldState: MyGameState): Result<MyGameState> {
        return actions.fold(Result.success(oldState), ::applyAllActions)
    }

    private fun applyAllActions(
        state: Result<MyGameState>,
        action: GameAction,
    ): Result<MyGameState> {
        return state.flatMap { action.apply(it) }
    }

}
