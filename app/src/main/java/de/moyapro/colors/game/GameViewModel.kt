package de.moyapro.colors.game

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

private const val TAG = "GameViewModel"

class GameViewModel : ViewModel() {

    private val actions: MutableList<GameAction> = mutableListOf()
    private val initialState = MyGameState(emptyList())
    private val _uiState: MutableStateFlow<Result<MyGameState>> =
        MutableStateFlow(Result.success(initialState))
    val uiState: StateFlow<Result<MyGameState>>
        get() = _uiState.asStateFlow()

    fun getCurrentGameState(): Result<MyGameState> {
        val initial = Result.success(initialState)
        val foldingFunction = { state: Result<MyGameState>, action: GameAction ->
            state.flatMap { action.apply(it) }
        }
        val result = actions.fold(initial, foldingFunction)
        if (result.isFailure) {
            undoLastAction()
        }
        return result
    }

    fun addAction(action: GameAction): GameViewModel {
        Log.i(TAG, "add action $action")
        this.actions.add(action)
        this._uiState.value = getCurrentGameState()
        return this
    }

    fun undoLastAction(): GameViewModel {
        val removedAction = this.actions.removeLast()
        Log.i(TAG, "removed action $removedAction")
        this._uiState.value = getCurrentGameState()
        return this
    }

}

fun <T> Result<T>.flatMap(transform: (T) -> Result<T>): Result<T> {
    return if (this.isSuccess)
        transform(this.getOrThrow())
    else this
}
