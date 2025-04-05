package de.moyapro.colors.game.persistance

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.model.gameState.FightData
import de.moyapro.colors.game.model.gameState.GameOptions
import de.moyapro.colors.game.model.gameState.GameState
import de.moyapro.colors.game.model.gameState.ProgressionData
import de.moyapro.colors.game.model.gameState.RunData
import de.moyapro.colors.game.model.gameState.notStartedFight
import de.moyapro.colors.initDefaultOptions
import de.moyapro.colors.initEmptyProgression
import de.moyapro.colors.initEmptyRun
import de.moyapro.colors.util.CURRENT_FIGHT_STATE_KEY
import de.moyapro.colors.util.CURRENT_RUN_STATE_KEY
import de.moyapro.colors.util.GAME_ACTIONS_KEY
import de.moyapro.colors.util.GAME_OPTIONS_STATE_KEY
import de.moyapro.colors.util.OVERALL_PROGRESSION_STATE_KEY
import de.moyapro.colors.util.getConfiguredJson
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

private const val TAG = "PersistenceService"

suspend fun loadSavedState(dataStore: DataStore<Preferences>): GameState =
    dataStore.data.map { preferences ->
        Log.d(TAG, "load current fight state: ${preferences[CURRENT_FIGHT_STATE_KEY]}")
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
        return@map gameState
    }.first()


fun save(dataStore: DataStore<Preferences>, gameState: GameState, actions: Collection<GameAction>? = null): Unit = runBlocking {
    dataStore.edit { settings ->
        settings[CURRENT_FIGHT_STATE_KEY] = getConfiguredJson().writeValueAsString(gameState.currentFight)
        settings[CURRENT_RUN_STATE_KEY] = getConfiguredJson().writeValueAsString(gameState.currentRun)
        settings[OVERALL_PROGRESSION_STATE_KEY] = getConfiguredJson().writeValueAsString(gameState.progression)
        settings[GAME_OPTIONS_STATE_KEY] = getConfiguredJson().writeValueAsString(gameState.options)
        if (actions != null) settings[GAME_ACTIONS_KEY] = getConfiguredJson().writeValueAsString(actions)
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
        dataStore.edit { settings ->
            settings[GAME_ACTIONS_KEY] = actionsJson
        }
    }
}

private inline fun <reified T> ObjectMapper.readOptionalValue(value: String?): T? {
    if (null == value) return null
    return this.readValue(value)
}
