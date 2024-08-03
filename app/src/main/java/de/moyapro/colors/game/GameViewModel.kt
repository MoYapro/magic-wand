package de.moyapro.colors.game

import android.util.*
import androidx.lifecycle.*
import de.moyapro.colors.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.generators.*
import de.moyapro.colors.game.model.gameState.*
import kotlinx.coroutines.flow.*

private const val TAG = "GameViewModel"

class GameViewModel(
    private val initialState: NewGameState = StartFightFactory.setupFightStage(
        listOf(createExampleWand()),
    ),
    private val saveActions: (Collection<GameAction>) -> Unit = {},
) : ViewModel() {

    private val actions: MutableList<GameAction> = mutableListOf()
    private val _uiState: MutableStateFlow<Result<NewGameState>> =
        MutableStateFlow(Result.success(initialState))
    val uiState: StateFlow<Result<NewGameState>>
        get() = _uiState.asStateFlow()

    fun getCurrentGameState(): Result<NewGameState> {
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
        state: Result<NewGameState>,
        action: GameAction,
    ): Result<NewGameState> {
        return state.flatMap { action.apply(it) }
    }

    fun addAction(action: GameAction): GameViewModel {
        Log.d(TAG, "Add action: $action")
        action.onAddAction(this.actions)
        this._uiState.value = getCurrentGameState()
        this._uiState.value.onSuccess { saveActions(this.actions) }
        return this
    }

}

fun <T> Result<T>.flatMap(transform: (T) -> Result<T>): Result<T> {
    return if (this.isSuccess)
        transform(this.getOrThrow())
    else this
}
