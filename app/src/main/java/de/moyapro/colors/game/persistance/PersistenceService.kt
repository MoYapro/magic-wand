package de.moyapro.colors.game.persistance

import android.util.*
import androidx.datastore.core.*
import androidx.datastore.preferences.core.*
import com.fasterxml.jackson.databind.*
import de.moyapro.colors.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.model.gameState.*
import de.moyapro.colors.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

suspend fun loadSavedState(dataStore: DataStore<Preferences>): GameState =
    dataStore.data.map { preferences ->
        val objectMapper = getConfiguredJson()
        val currentFight: FightData? = objectMapper.readValue(preferences[CURRENT_FIGHT_STATE_KEY])
        val currentRun: RunData? = objectMapper.readValue(preferences[CURRENT_RUN_STATE_KEY])
        val progression: ProgressionData? = objectMapper.readValue(preferences[OVERALL_PROGRESSION_STATE_KEY])
        val options: GameOptions? = objectMapper.readValue(preferences[GAME_OPTIONS_STATE_KEY])
        val gameState = GameState(
            currentFight = currentFight ?: notStartedFight(),
            currentRun = currentRun ?: initEmptyRun(),
            progression = progression ?: initEmptyProgression(),
            options = options ?: initDefaultOptions()
        )
        Log.d("Load game state", getConfiguredJson().writeValueAsString(gameState))
        return@map gameState
    }.first()


fun save(dataStore: DataStore<Preferences>, gameState: GameState): Unit = runBlocking {
    dataStore.edit { settings ->
        settings[CURRENT_FIGHT_STATE_KEY] = getConfiguredJson().writeValueAsString(gameState.currentFight)
        settings[CURRENT_RUN_STATE_KEY] = getConfiguredJson().writeValueAsString(gameState.currentRun)
        settings[OVERALL_PROGRESSION_STATE_KEY] = getConfiguredJson().writeValueAsString(gameState.progression)
        settings[GAME_OPTIONS_STATE_KEY] = getConfiguredJson().writeValueAsString(gameState.options)
    }
}

fun loadActions(dataStore: DataStore<Preferences>) = runBlocking {
    dataStore.data.map { preferences ->
        val actions: Collection<GameAction> = getConfiguredJson().readValue(preferences[GAME_ACTIONS_KEY]) ?: emptyList()
        return@map actions
    }
}


fun saveActions(dataStore: DataStore<Preferences>, actions: Collection<GameAction>) = runBlocking {
    dataStore.edit { settings ->
        settings[GAME_ACTIONS_KEY] = getConfiguredJson().writeValueAsString(actions)
    }
}


private inline fun <reified T> ObjectMapper.readValue(value: String?): T? {
    if (null == value) return null
    return this.readValue(value, T::class.java)
}
