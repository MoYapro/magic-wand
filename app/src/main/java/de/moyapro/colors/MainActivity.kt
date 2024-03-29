package de.moyapro.colors

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import de.moyapro.colors.ui.theme.ColorsTheme
import de.moyapro.colors.util.GAME_SAVE_STATE
import kotlinx.coroutines.flow.map

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        val isSavegamePresent = isSavegamePresent()
        super.onCreate(savedInstanceState)
        setContent {
            ColorsTheme {
                val menuActions = mutableListOf(
                    "Next fight" to ::startFightActivity,
                    "Edit wands" to ::startEditWandsActivity
                )
                if (isSavegamePresent) menuActions.add(
                    "New game" to ::resetProgress
                )
                MainMenu(
                    menuActions
                )
            }
        }
    }

    private fun isSavegamePresent(): Boolean {
        return dataStore.data.map { preferences ->
            val s = preferences[GAME_SAVE_STATE]
            s
        } == null
    }

    private fun resetProgress() {
        deleteDatabase(GAME_SAVE_STATE.name)
    }


    private fun startFightActivity() {
        this.startActivity(Intent(this, FightActivity::class.java))
    }

    private fun startEditWandsActivity() {
        this.startActivity(Intent(this, EditWandsActivity::class.java))
    }

}
