package de.moyapro.colors.game

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fasterxml.jackson.module.kotlin.readValue
import de.moyapro.colors.util.GAME_SAVE_STATE
import de.moyapro.colors.util.getConfiguredJson
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class GameViewModelFactory(private val dataStore: DataStore<Preferences>) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        check(modelClass.isAssignableFrom(GameViewModel::class.java)) { "Cannot create GameViewModel from ${modelClass::class}" }
        return runBlocking {
            val initialGameState = dataStore.data.map { preferences ->
                val jsonData = preferences[GAME_SAVE_STATE]
                jsonData?.let { data -> getConfiguredJson().readValue<MyGameState>(data) }
            }
            val initialState = initialGameState.first() ?: StartFightFactory.createInitialState()
            return@runBlocking GameViewModel(initialState) as T
        }
    }
}