package de.moyapro.colors.game

import android.util.*
import androidx.lifecycle.*
import de.moyapro.colors.*
import de.moyapro.colors.game.actions.*
import kotlinx.coroutines.flow.*

private const val TAG = "GameViewModel"

class GameViewModel(
    private val initialState: MyGameState = StartFightFactory.setupFightStage(
        listOf(createExampleWand()),
    ),
    private val saveFightState: (MyGameState) -> Unit = {},
) : ViewModel() {

    private val actions: MutableList<GameAction> = mutableListOf()
    private val _uiState: MutableStateFlow<Result<MyGameState>> =
        MutableStateFlow(Result.success(initialState))
    val uiState: StateFlow<Result<MyGameState>>
        get() = _uiState.asStateFlow()

    fun getCurrentGameState(): Result<MyGameState> {
        val initial = Result.success(initialState)
        val result = actions.fold(initial, ::applyAllActions)
        if (result.isFailure) {
            Log.e(TAG, "Error in action '${actions.last()}': $result")
            addAction(UndoAction) // TODO: This is not shown because result below is put in stateFlow last
            return getCurrentGameState()
        }
        return result
    }

    private fun applyAllActions(
        state: Result<MyGameState>,
        action: GameAction,
    ): Result<MyGameState> {
        return state.flatMap { action.apply(it) }
    }

    fun addAction(action: GameAction): GameViewModel {
        Log.d(TAG, "Add action: $action")
        action.onAddAction(this.actions)
        this._uiState.value = getCurrentGameState()
        this._uiState.value.onSuccess(saveFightState)
        return this
    }

}

fun <T> Result<T>.flatMap(transform: (T) -> Result<T>): Result<T> {
    return if (this.isSuccess)
        transform(this.getOrThrow())
    else this
}
