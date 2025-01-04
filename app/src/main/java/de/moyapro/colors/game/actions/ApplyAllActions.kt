package de.moyapro.colors.game.actions

import de.moyapro.colors.game.model.gameState.GameState

fun applyAllActions(state: Result<GameState>, action: GameAction): Result<GameState> {
    return state.flatMap { action.apply(it) }
}

private fun <T> Result<T>.flatMap(transform: (T) -> Result<T>): Result<T> {
    return if (this.isSuccess)
        transform(this.getOrThrow())
    else this
}

