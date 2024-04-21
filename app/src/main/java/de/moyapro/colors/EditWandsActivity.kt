package de.moyapro.colors

import android.content.*
import android.os.*
import android.widget.*
import androidx.activity.*
import androidx.activity.compose.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.unit.*
import androidx.datastore.core.*
import androidx.datastore.preferences.*
import androidx.datastore.preferences.core.*
import de.moyapro.colors.game.*
import de.moyapro.colors.ui.theme.*
import de.moyapro.colors.util.*
import kotlinx.coroutines.*

private const val TAG = "EditWandsActivity"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "gameSaveState")


class EditWandsActivity : ComponentActivity() {

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
                        LootSpellsView(
                            Modifier
                                .fillMaxWidth()
                                .height(5 * SPELL_SIZE.dp),
                            currentGameState = currentGameState,
                            spells = currentGameState.loot.spells,
                            addAction = gameViewModel::addAction,
                        )
                        Spacer(modifier = Modifier.height(48.dp))
                        WandsEditView(
                            Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(.5f),
                            currentGameState,
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
