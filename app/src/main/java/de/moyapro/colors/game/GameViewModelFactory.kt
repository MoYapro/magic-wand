package de.moyapro.colors.game

import androidx.datastore.core.*
import androidx.datastore.preferences.core.*
import androidx.lifecycle.*
import com.fasterxml.jackson.module.kotlin.*
import de.moyapro.colors.game.generators.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.gameState.*
import de.moyapro.colors.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class GameViewModelFactory(private val dataStore: DataStore<Preferences>) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        check(modelClass.isAssignableFrom(GameViewModel::class.java)) { "Cannot create GameViewModel from ${modelClass::class}" }
        return runBlocking {
            val gameState = loadFightState()
            val wandState = if (null == gameState) {
                loadWandState()
            } else {
                null
            }
            check(gameState != null || wandState != null) { "Cannot open fight without wands" }
            val state = StartFightFactory.setupFightStage(fightState = gameState?.currentFight, wands = wandState)
            return@runBlocking GameViewModel(state, ::saveFightState) as T
        }
    }

    private suspend fun loadWandState() = dataStore.data.map { preferences ->
        val jsonData = preferences[WAND_STATE]
        jsonData?.let { data -> getConfiguredJson().readValue<List<Wand>>(data) }
    }.first()

    private suspend fun loadFightState() = dataStore.data.map { preferences ->
        val jsonData = preferences[CURRENT_FIGHT_STATE_KEY]
        jsonData?.let { data -> getConfiguredJson().readValue<NewGameState>(data) }
    }.first()

    private fun saveFightState(state: NewGameState): Unit = runBlocking {
        dataStore.edit { settings ->
            settings[CURRENT_FIGHT_STATE_KEY] = getConfiguredJson().writeValueAsString(state)
        }
    }
}