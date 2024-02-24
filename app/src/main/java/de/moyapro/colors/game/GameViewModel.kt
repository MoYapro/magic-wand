package de.moyapro.colors.game

class GameViewModel {
    private val actions: MutableList<GameAction> = mutableListOf()
    private val initialState = MyGameState(emptyList())
    fun getCurrentGameState(): MyGameState {
        return actions.fold(initialState) { state: MyGameState, action: GameAction ->
            action.apply(
                state
            )
        }
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
