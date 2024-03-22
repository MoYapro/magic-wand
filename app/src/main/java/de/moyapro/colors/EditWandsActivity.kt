package de.moyapro.colors

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.moyapro.colors.game.GameViewModel
import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.game.actions.AddSpellToStashAction
import de.moyapro.colors.ui.theme.ColorsTheme
import de.moyapro.colors.wand.Spell

private const val TAG = "EditWandsActivity"

class EditWandsActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private val gameViewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gameViewModel.addAction(AddSpellToStashAction(Spell(name = "One")))
        gameViewModel.addAction(AddSpellToStashAction(Spell(name = "Two")))
        setContent {
            val currentGameStateResult: Result<MyGameState> by gameViewModel.uiState.collectAsState()
            val currentGameState = currentGameStateResult.getOrThrow()
            Log.d(TAG, "spells: ${currentGameState.spellsInStash}")
            Log.d(
                TAG,
                "wands: ${currentGameState.wands.map { it.slots.map { it.spell } }.flatten()}"
            )
            ColorsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black,

                    ) {
                    Column(Modifier.fillMaxSize()) {
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                        ) {
                            items(
                                items = currentGameState.spellsInStash,
                                key = { it.id.hashCode() }) { spell ->
                                Draggable(dataToDrop = spell, viewModel = mainViewModel) {
                                    SpellView(spell = spell)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        val wands = currentGameState.wands
                        if (wands.size > 0) WandEditView(
                            gameViewModel = gameViewModel,
                            wandData = wands[0]
                        )
                        if (wands.size > 1) WandEditView(
                            gameViewModel = gameViewModel,
                            wandData = wands[1]
                        )
                        if (wands.size > 2) WandEditView(
                            gameViewModel = gameViewModel,
                            wandData = wands[2]
                        )
                    }
                }
            }
        }
    }
}
