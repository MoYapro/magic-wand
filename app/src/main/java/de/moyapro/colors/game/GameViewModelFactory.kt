package de.moyapro.colors.game

import androidx.datastore.core.*
import androidx.datastore.preferences.core.*
import androidx.lifecycle.*
import com.fasterxml.jackson.module.kotlin.*
import de.moyapro.colors.takeTwo.*
import de.moyapro.colors.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class GameViewModelFactory(private val dataStore: DataStore<Preferences>) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        check(modelClass.isAssignableFrom(GameViewModel::class.java)) { "Cannot create GameViewModel from ${modelClass::class}" }
        return runBlocking {
            val fightState = loadFightState()
            val wandState = if (null == fightState) {
                loadWandState()
            } else {
                null
            }
            check(fightState != null || wandState != null) { "Cannot open fight without wands" }
            val state = StartFightFactory.setupFightStage(fightState = fightState, wands = wandState)
            return@runBlocking GameViewModel(state, ::saveFightState) as T
        }
    }

    private suspend fun loadWandState() = dataStore.data.map { preferences ->
        val jsonData = preferences[WAND_STATE]
        jsonData?.let { data -> getConfiguredJson().readValue<List<Wand>>(data) }
    }.first()

    private suspend fun loadFightState() = dataStore.data.map { preferences ->
        val jsonData = preferences[FIGHT_STATE]
        jsonData?.let { data -> getConfiguredJson().readValue<MyGameState>(data) }
    }.first()

    private fun saveFightState(state: MyGameState): Unit = runBlocking {
        dataStore.edit { settings ->
            settings[FIGHT_STATE] = getConfiguredJson().writeValueAsString(state)
        }
    }
}