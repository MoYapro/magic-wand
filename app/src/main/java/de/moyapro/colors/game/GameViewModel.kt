package de.moyapro.colors.game

import android.util.*
import androidx.lifecycle.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.generators.*
import de.moyapro.colors.game.model.gameState.*
import kotlinx.coroutines.flow.*

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

    fun getCurrentGameState(): Result<GameState> {
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