package de.moyapro.colors.game.persistance

import android.util.*
import androidx.datastore.core.*
import androidx.datastore.preferences.core.*
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.module.kotlin.*
import de.moyapro.colors.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.model.gameState.*
import de.moyapro.colors.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

private const val TAG = "PersistenceService"

suspend fun loadSavedState(dataStore: DataStore<Preferences>): GameState =
    dataStore.data.map { preferences ->
        val objectMapper = getConfiguredJson()
        val currentFight: FightData? = objectMapper.readOptionalValue(preferences[CURRENT_FIGHT_STATE_KEY])
        val currentRun: RunData? = objectMapper.readOptionalValue(preferences[CURRENT_RUN_STATE_KEY])
        val progression: ProgressionData? = objectMapper.readOptionalValue(preferences[OVERALL_PROGRESSION_STATE_KEY])
        val options: GameOptions? = objectMapper.readOptionalValue(preferences[GAME_OPTIONS_STATE_KEY])
        val gameState = GameState(
            currentFight = currentFight ?: notStartedFight(),
            currentRun = currentRun ?: initEmptyRun(),
            progression = progression ?: initEmptyProgression(),
            options = options ?: initDefaultOptions()
        )
        Log.d(TAG, "loaded data: ${getConfiguredJson().writeValueAsString(gameState)}")
        return@map gameState
    }.first()


fun save(dataStore: DataStore<Preferences>, gameState: GameState): Unit = runBlocking {
    dataStore.edit { settings ->
        Log.d(TAG, "save current game state")
        settings[CURRENT_FIGHT_STATE_KEY] = getConfiguredJson().writeValueAsString(gameState.currentFight)
        settings[CURRENT_RUN_STATE_KEY] = getConfiguredJson().writeValueAsString(gameState.currentRun)
        settings[OVERALL_PROGRESSION_STATE_KEY] = getConfiguredJson().writeValueAsString(gameState.progression)
        settings[GAME_OPTIONS_STATE_KEY] = getConfiguredJson().writeValueAsString(gameState.options)
    }
}

fun loadActions(dataStore: DataStore<Preferences>): Collection<GameAction> = runBlocking {
    dataStore.data.map { preferences ->
        val actionsJson = preferences[GAME_ACTIONS_KEY]
        Log.d(TAG, "load actions json: $actionsJson")
        val actions: Collection<GameAction> = getConfiguredJson().readOptionalValue(actionsJson) ?: emptyList()
        Log.d(TAG, "load actions object: $actions")
        require(actions.all { it is GameAction }) { "failed to load game actions => are they mapped correctly" }
        return@map actions
    }.first()
}


fun saveActions(dataStore: DataStore<Preferences>, actions: Collection<GameAction>): Preferences {
    val actionsJson = getConfiguredJson().writeValueAsString(actions)
    return runBlocking {
        Log.d(TAG, "Save Actions: $actionsJson")
        dataStore.edit { settings ->
            settings[GAME_ACTIONS_KEY] = actionsJson
        }
    }
}

private inline fun <reified T> ObjectMapper.readOptionalValue(value: String?): T? {
    if (null == value) return null
    return this.readValue(value)
}
