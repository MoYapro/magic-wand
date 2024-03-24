package de.moyapro.colors

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import de.moyapro.colors.game.GameViewModel
import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.game.actions.AddSpellToStashAction
import de.moyapro.colors.game.actions.AddWandAction
import de.moyapro.colors.game.actions.PlaceSpellInStashAction
import de.moyapro.colors.ui.theme.ColorsTheme
import de.moyapro.colors.util.DROP_ZONE_ALPHA
import de.moyapro.colors.util.SPELL_SIZE
import de.moyapro.colors.wand.Spell

private const val TAG = "EditWandsActivity"

class EditWandsActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private val gameViewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repeat(10) {
            gameViewModel.addAction(AddSpellToStashAction(Spell(name = "One $it")))
            gameViewModel.addAction(AddSpellToStashAction(Spell(name = "Two $it")))
        }
        gameViewModel.addAction(AddWandAction(createExampleWand()))
        gameViewModel.addAction(AddWandAction(createExampleWand()))
        setContent {
            val currentGameStateResult: Result<MyGameState> by gameViewModel.uiState.collectAsState()
            val currentGameState: MyGameState = currentGameStateResult.getOrElse {
                Toast.makeText(LocalContext.current, it.message, Toast.LENGTH_LONG).show()
                MyGameState(emptyList(), emptyList(), emptyList(), 0, emptyList())
            }


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
                        DropZone<Spell>(
                            modifier = Modifier.border(BorderStroke(1.dp, Color.LightGray)),
                            gameViewModel = gameViewModel,
                            condition = { state, dragData -> !state.spellsInStash.contains(dragData) }
                        ) { isInBound: Boolean, droppedSpell: Spell?, hoveredSpell: Spell? ->
                            val canDrop: Boolean =
                                isInBound && !currentGameState.spellsInStash.contains(hoveredSpell)
                            if (canDrop && null != droppedSpell) {
                                LaunchedEffect(key1 = droppedSpell) {
                                    gameViewModel.addAction(PlaceSpellInStashAction(droppedSpell.id))
                                }
                            }

                            LazyVerticalGrid(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(250.dp)
                                    .background(color = if (canDrop) Color.Green.copy(alpha = DROP_ZONE_ALPHA) else Color.Transparent),
                                columns = GridCells.FixedSize(SPELL_SIZE.dp),
                                verticalArrangement = Arrangement.SpaceEvenly,
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                userScrollEnabled = false,
                            ) {
                                items(
                                    items = currentGameState.spellsInStash,
                                    key = { it.id.hashCode() }) { spell ->
                                    Draggable(
                                        modifier = Modifier
                                            .height(SPELL_SIZE.dp)
                                            .width(SPELL_SIZE.dp)
                                            .border(1.dp, Color.LightGray)
                                            .align(Alignment.Center),
                                        dataToDrop = spell,
                                        viewModel = mainViewModel
                                    ) {
                                        SpellView(spell = spell)
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(48.dp))
                        val wands = currentGameState.wands
                        Row {

                            if (wands.size > 0) WandEditView(
                                modifier = Modifier
                                    .fillMaxWidth(1f / 3f)
                                    .fillMaxHeight(),
                                gameViewModel = gameViewModel,
                                mainViewModel = mainViewModel,
                                wandData = wands[0]
                            )
                            if (wands.size > 1) WandEditView(
                                modifier = Modifier.fillMaxWidth(1f / 2f),
                                gameViewModel = gameViewModel,
                                mainViewModel = mainViewModel,
                                wandData = wands[1]
                            )
                            if (wands.size > 2) WandEditView(
                                modifier = Modifier.fillMaxWidth(1f),
                                gameViewModel = gameViewModel,
                                mainViewModel = mainViewModel,
                                wandData = wands[2]
                            )
                        }
                    }
                }
            }
        }
    }
}
