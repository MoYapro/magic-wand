package de.moyapro.colors

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import de.moyapro.colors.game.GameViewModel
import de.moyapro.colors.game.GameViewModelFactory
import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.ui.theme.ColorsTheme
import de.moyapro.colors.util.SPELL_SIZE
import de.moyapro.colors.util.WAND_STATE
import de.moyapro.colors.util.getConfiguredJson
import kotlinx.coroutines.runBlocking

private const val TAG = "EditWandsActivity"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "gameSaveState")


class EditWandsActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private val gameViewModel: GameViewModel by viewModels {
        GameViewModelFactory(this.dataStore)
    }

    fun save(gameState: MyGameState): Unit = runBlocking {
        dataStore.edit { settings ->
            settings[WAND_STATE] = getConfiguredJson().writeValueAsString(gameState.wands)
        }
    }

    fun backToMainMenu() {
        this.startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activity: EditWandsActivity = this
        setContent {
            val currentGameStateResult: Result<MyGameState> by gameViewModel.uiState.collectAsState()
            val currentGameState: MyGameState = currentGameStateResult.getOrElse {
                Toast.makeText(LocalContext.current, it.message, Toast.LENGTH_LONG).show()
                MyGameState(emptyList(), emptyList(), emptyList(), 0, emptyList())
            }

            ColorsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black,

                    ) {
                    Column(Modifier.fillMaxSize()) {
                        StashView(
                            Modifier
                                .fillMaxWidth()
                                .height(4 * SPELL_SIZE.dp),
                            currentGameState = currentGameState,
                            mainViewModel = mainViewModel,
                            addAction = gameViewModel::addAction,
                        )
                        Spacer(modifier = Modifier.height(48.dp))
                        WandsEditView(
                            Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(.5f),
                            currentGameState,
                            mainViewModel,
                            gameViewModel::addAction
                        )
                        Spacer(modifier = Modifier.height(48.dp))
                        WandEditButtonBarView(
                            activity::backToMainMenu,
                            activity::save,
                            currentGameState
                        )
                    }
                }
            }
        }
    }
}
