package de.moyapro.colors.game

import android.util.Log
import androidx.lifecycle.ViewModel
import de.moyapro.colors.createExampleEnemy
import de.moyapro.colors.createExampleMagic
import de.moyapro.colors.createExampleWand
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.actions.UndoAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

private const val TAG = "GameViewModel"

class GameViewModel(
    private val initialState: MyGameState =
        MyGameState(
            listOf(createExampleEnemy(), createExampleEnemy()),
            listOf(createExampleWand()),
            listOf(createExampleMagic()),
            0,
            emptyList(),
        ),
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
        action.onAddAction(this.actions)
        this._uiState.value = getCurrentGameState()
        return this
    }

    fun saveWands() {
        TODO("Not yet implemented")
    }
}

fun <T> Result<T>.flatMap(transform: (T) -> Result<T>): Result<T> {
    return if (this.isSuccess)
        transform(this.getOrThrow())
    else this
}
