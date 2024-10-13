package de.moyapro.colors.game

import androidx.datastore.core.*
import androidx.datastore.preferences.core.*
import androidx.lifecycle.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.persistance.*
import kotlinx.coroutines.*

class GameViewModelFactory(private val dataStore: DataStore<Preferences>) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        check(modelClass.isAssignableFrom(GameViewModel::class.java)) { "Cannot create GameViewModel from ${modelClass::class}" }
        return runBlocking {
            val gameState = loadSavedState(dataStore)
            val actions = loadActions(dataStore)
            val saveActions: (Collection<GameAction>) -> Unit = { actionsToSave -> saveActions(dataStore, actionsToSave) }
            val loadActions: () -> Collection<GameAction> = { loadActions(dataStore) }
            return@runBlocking GameViewModel(gameState, actions, saveActions, loadActions) as T
        }
    }
}