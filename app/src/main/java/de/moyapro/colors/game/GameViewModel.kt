package de.moyapro.colors.game

import android.util.Log
import androidx.lifecycle.ViewModel
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.actions.UndoAction
import de.moyapro.colors.game.actions.applyAllActions
import de.moyapro.colors.game.generators.Initializer
import de.moyapro.colors.game.model.gameState.GameState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

private const val TAG = "GameViewModel"

class GameViewModel(
    private val initialState: GameState = Initializer.createInitialGameState(),
    initialActions: Collection<GameAction> = emptyList(),
    private val saveActions: (Collection<GameAction>) -> Unit,
    private val loadActions: () -> Collection<GameAction>,
) : ViewModel() {

    init {
        Log.d(TAG, "Create new GameViewModel")
    }

    private val actions: MutableList<GameAction> = initialActions.toMutableList()
    private val _uiState: MutableStateFlow<Result<GameState>> = MutableStateFlow(Result.success(initialState))
    val uiState: StateFlow<Result<GameState>>
        get() = _uiState.asStateFlow()

    private fun getCurrentGameState(): Result<GameState> {
        val initial = Result.success(initialState)
        Log.d(TAG, "get current game state from initial: $initialState")
        val result = actions.fold(initial, ::applyAllActions)
        if (result.isFailure) {
            Log.e(TAG, "Error in action '${actions.last()}': $result")
            addAction(UndoAction) // TODO: This is not shown because result below is put in stateFlow last
            return getCurrentGameState()
        }
        return result
    }

    fun addAction(action: GameAction): GameViewModel {
        Log.d(TAG, "Add action: $action")
        action.onAddAction(this.actions)
        this._uiState.value = getCurrentGameState()
        this._uiState.value.onSuccess { saveActions(this.actions) }
        return this
    }

    fun reloadActions() {
        this.actions.clear()
        this.actions.addAll(loadActions())
        this._uiState.value = getCurrentGameState()
    }

}