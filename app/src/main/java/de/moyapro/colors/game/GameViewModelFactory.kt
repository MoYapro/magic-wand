package de.moyapro.colors.game

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fasterxml.jackson.module.kotlin.readValue
import de.moyapro.colors.takeTwo.Wand
import de.moyapro.colors.util.FIGHT_STATE
import de.moyapro.colors.util.WAND_STATE
import de.moyapro.colors.util.getConfiguredJson
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

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
            val state =
                fightState ?: StartFightFactory.createInitialState(wands = wandState)
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