package de.moyapro.colors.game.actions

import de.moyapro.colors.game.model.gameState.*

fun applyAllActions(state: Result<NewGameState>, action: GameAction): Result<NewGameState> {
    return state.flatMap { action.apply(it) }
}

private fun <T> Result<T>.flatMap(transform: (T) -> Result<T>): Result<T> {
    return if (this.isSuccess)
        transform(this.getOrThrow())
    else this
}

