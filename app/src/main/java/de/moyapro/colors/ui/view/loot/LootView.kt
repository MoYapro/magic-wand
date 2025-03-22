package de.moyapro.colors.ui.view.loot

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.moyapro.colors.createExampleWand
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.actions.loot.ClaimLootAction
import de.moyapro.colors.game.generators.Initializer
import de.moyapro.colors.game.model.Spell
import de.moyapro.colors.game.model.Wand
import de.moyapro.colors.game.model.gameState.GameState
import de.moyapro.colors.game.spell.Acid
import de.moyapro.colors.game.spell.Fizz
import de.moyapro.colors.game.spell.Splash
import de.moyapro.colors.ui.view.components.SpellGrid
import de.moyapro.colors.ui.view.components.WandGrid
import de.moyapro.colors.util.MAGE_II_ID
import de.moyapro.colors.util.MAGE_I_ID

const val TAG = "LootView"


@Composable
fun LootView(
    newSpells: List<Spell<*>>,
    newWands: List<Wand>,
    currentGameState: GameState,
    addAction: (GameAction) -> Unit = {},
    goToNextScreenAction: () -> Unit,
) {
    var selectedSpells: List<Spell<*>> by remember { mutableStateOf(listOf()) }
    Log.d(TAG, "selectedSpells: $selectedSpells")
    var selectedWands: List<Wand> by remember { mutableStateOf(listOf()) }
    Column(modifier = Modifier.fillMaxSize()) {
        SpellGrid(
            spells = newSpells,
            highlightedSpells = selectedSpells.map { it.id },
            clickSpellAction = { spell -> if (selectedSpells.contains(spell)) selectedSpells -= spell else selectedSpells += spell }
        )
        WandGrid(
            wands = newWands,
            highlightedWands = selectedWands.map { it.id },
            clickWandAction = { wand -> if (selectedWands.contains(wand)) selectedWands -= wand else selectedWands += wand },
            currentGameState = currentGameState
        )
        SpellGrid(currentGameState.currentRun.spells) { }
        Button(
            onClick = {
                addAction(ClaimLootAction(selectedSpells, selectedWands))
                goToNextScreenAction()
            }
        ) {
            Text("Claim")
        }
    }
}

@Composable
@Preview
fun PreviewLootView() {
    val newSpells: List<Spell<*>> = listOf(Fizz(), Acid(), Splash())
    val newWands: List<Wand> = listOf(createExampleWand(mageId = MAGE_I_ID), createExampleWand(mageId = MAGE_II_ID))
    LootView(newSpells, newWands, goToNextScreenAction = {}, currentGameState = Initializer.createInitialGameState())
}