package de.moyapro.colors.ui.view.loot

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.actions.loot.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.gameState.*
import de.moyapro.colors.ui.view.components.*
import de.moyapro.colors.ui.view.dragdrop.*
import de.moyapro.colors.util.*

private const val TAG = "LootSpellsView"

@Composable
fun LootSpellsView(modifier: Modifier = Modifier, currentGameState: NewGameState, addAction: (GameAction) -> GameViewModel) {
    DropZone<Spell>(
        modifier = modifier.border(BorderStroke(1.dp, Color.LightGray)),
        condition = { state, dragData -> !state.currentRun.spells.contains(dragData) },
        onDropAction = { droppedSpell -> PlaceSpellInLootAction(droppedSpell) },
        currentGameState = currentGameState,
        addAction = addAction,
    )
    { dropModifier: Modifier, isInBound: Boolean, hoveredSpell: Spell? ->
        LazyVerticalGrid(
            modifier = dropModifier
                .fillMaxWidth()
                .height(2 * SPELL_SIZE.dp),
            columns = GridCells.FixedSize(SPELL_SIZE.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalArrangement = Arrangement.SpaceEvenly,
            userScrollEnabled = false,
        ) {
            items(
                items = currentGameState.currentRun.spells,
                key = { it.hashCode() }) { spell ->
                Draggable(
                    modifier = Modifier
                        .height(SPELL_SIZE.dp)
                        .width(SPELL_SIZE.dp)
                        .border(1.dp, Color.LightGray)
                        .align(Alignment.Center),
                    dataToDrop = spell,
                    onDropAction = RemoveSpellFromLootAction(spell),
                    onDropDidReplaceAction = { replaceSpell -> PlaceSpellInLootAction(replaceSpell) }
                ) { theSpell, _ ->
                    SpellView(spell = theSpell)
                }
            }
        }
    }
}
