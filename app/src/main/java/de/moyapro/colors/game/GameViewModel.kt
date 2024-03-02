package de.moyapro.colors.game

class GameViewModel {
    private val actions: MutableList<GameAction> = mutableListOf()
    private val initialState = MyGameState(emptyList())
    fun getCurrentGameState(): Result<MyGameState> {
        val initial = Result.success(initialState)
        val foldingFunction = { state: Result<MyGameState>, action: GameAction ->
            state.flatMap { action.apply(it) }
        }
        return actions.fold(initial, foldingFunction)
    }

    fun addAction(action: GameAction): GameViewModel {
        this.actions.add(action)
        return this
    }

    fun undoLastAction(): GameViewModel {
        this.actions.removeLast()
        return this
    }

}

fun <T> Result<T>.flatMap(transform: (T) -> Result<T>): Result<T> {
    return if (this.isSuccess)
        transform(this.getOrThrow())
    else this
}
