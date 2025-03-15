package de.moyapro.colors.ui.view.loot

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import de.moyapro.colors.createExampleWand
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.actions.loot.ClaimLootAction
import de.moyapro.colors.game.generators.Initializer
import de.moyapro.colors.game.model.Bonk
import de.moyapro.colors.game.model.Fizz
import de.moyapro.colors.game.model.Spell
import de.moyapro.colors.game.model.Splash
import de.moyapro.colors.game.model.Wand
import de.moyapro.colors.game.model.gameState.GameState
import de.moyapro.colors.ui.view.components.SpellView
import de.moyapro.colors.ui.view.components.WandView
import de.moyapro.colors.util.MAGE_II_ID
import de.moyapro.colors.util.MAGE_I_ID
import de.moyapro.colors.util.SPELL_SIZE

const val TAG = "LootView"


@Composable
fun LootView(
    newSpells: List<Spell<*>>,
    newWands: List<Wand>,
    currentGameState: GameState,
    addAction: (GameAction) -> Unit = {},
    goToNextScreenAction: (GameState) -> Unit,
) {
    var selectedSpells: List<Spell<*>> by remember { mutableStateOf(listOf()) }
    Log.d(TAG, "selectedSpells: $selectedSpells")
    var selectedWands: List<Wand> by remember { mutableStateOf(listOf()) }
    Column(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .height(2 * SPELL_SIZE.dp),
            columns = GridCells.FixedSize(SPELL_SIZE.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalArrangement = Arrangement.SpaceEvenly,
            userScrollEnabled = false,
        ) {
            items(items = newSpells, key = { spell -> spell.id.hashCode() }) { spell ->
                SpellHighlight(
                    view = { SpellView(spell = spell, clickAction = { if (selectedSpells.contains(spell)) selectedSpells -= spell else selectedSpells += spell }) },
                    highlight = newSpells.contains(spell)
                )
            }
        }
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .height(2 * SPELL_SIZE.dp),
            columns = GridCells.FixedSize(SPELL_SIZE.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalArrangement = Arrangement.SpaceEvenly,
            userScrollEnabled = false,
        ) {
            items(items = newWands, key = { wand -> wand.id.hashCode() }) { wand ->
                WandView(
                    wand = wand,
                    addAction = {},
                    currentGameState = currentGameState,
                    clickAction = { if (selectedWands.contains(wand)) selectedWands -= wand else selectedWands += wand }
                )
            }
        }
        Button(
            onClick = {
                addAction(ClaimLootAction(selectedSpells, selectedWands))
                goToNextScreenAction(currentGameState)
            }
        ) {
            Text("Claim")
        }
    }
}

@Composable
fun SpellHighlight(view: @Composable () -> Unit, highlight: Boolean) {
    Box(modifier = Modifier.background(if (highlight) Color.Cyan else Color.Transparent)) {
        Log.d(
            TAG, "highlight: $highlight"
        )
        view()
    }
}

@Composable
@Preview
fun PreviewLootView() {
    val newSpells: List<Spell<*>> = listOf(Fizz(), Bonk(), Splash())
    val newWands: List<Wand> = listOf(createExampleWand(mageId = MAGE_I_ID), createExampleWand(mageId = MAGE_II_ID))
    LootView(newSpells, newWands, goToNextScreenAction = {}, currentGameState = Initializer.createInitialGameState())
}