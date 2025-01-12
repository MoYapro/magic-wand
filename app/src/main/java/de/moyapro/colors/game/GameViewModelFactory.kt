package de.moyapro.colors.game

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.model.gameState.GameState
import de.moyapro.colors.game.persistance.loadActions
import de.moyapro.colors.game.persistance.loadSavedState
import de.moyapro.colors.game.persistance.save
import de.moyapro.colors.game.persistance.saveActions
import kotlinx.coroutines.runBlocking

class GameViewModelFactory(private val dataStore: DataStore<Preferences>) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        check(modelClass.isAssignableFrom(GameViewModel::class.java)) { "Cannot create GameViewModel from ${modelClass::class}" }
        return runBlocking {
            val gameState = loadSavedState(dataStore)
            val actions = loadActions(dataStore)
            val saveActions: (Collection<GameAction>) -> Unit = { actionsToSave -> saveActions(dataStore, actionsToSave) }
            val loadActions: () -> Collection<GameAction> = { loadActions(dataStore) }
            val saveState: (GameState, Collection<GameAction>) -> Unit = { gameStateToSave, actionsToSave -> save(dataStore, gameStateToSave, actionsToSave) }
            return@runBlocking GameViewModel(gameState, actions, saveActions, loadActions, saveState) as T
        }
    }
}