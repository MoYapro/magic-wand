package de.moyapro.colors.util

import androidx.datastore.preferences.core.*

const val SPELL_SIZE = 64
const val ENEMY_SIZE = 72
const val MAGIC_SIZE = 16
const val DROP_ZONE_ALPHA = .3f
val CURRENT_FIGHT_STATE_KEY = stringPreferencesKey("current_fight_state")
val CURRENT_RUN_STATE_KEY = stringPreferencesKey("current_run_state_key")
val MAP_STATE_KEY = stringPreferencesKey("map_state_key")
val OVERALL_PROGRESSION_STATE_KEY = stringPreferencesKey("overall_progression_state_key")
val GAME_OPTIONS_STATE_KEY = stringPreferencesKey("game_options_state_key")
val GAME_ACTIONS_KEY = stringPreferencesKey("game_actions")
