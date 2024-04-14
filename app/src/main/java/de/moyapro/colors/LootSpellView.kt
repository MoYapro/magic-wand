package de.moyapro.colors

import android.util.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.util.*
import de.moyapro.colors.wand.*

private const val TAG = "LootSpellsView"
@Composable
fun LootSpellsView(modifier: Modifier = Modifier, spells: List<Spell>, currentGameState: MyGameState, addAction: (GameAction) -> GameViewModel) {
    DropZone<Spell>(
        modifier = modifier.border(BorderStroke(1.dp, Color.LightGray)),
        condition = { state, dragData ->
            Log.d(TAG, "current drag data: $dragData")
            !state.loot.spells.contains(dragData)
        },
        onDropAction = { droppedSpell -> PlaceSpellInLootAction(droppedSpell) },
        currentGameState = currentGameState,
        addAction = addAction,
    )
    { modifier: Modifier, isInBound: Boolean, droppedSpell: Any?, hoveredSpell: Any? ->
        val useDroppedSpell: Spell? = castOrNull(droppedSpell)
        val useHoveredSpell: Spell? = castOrNull(hoveredSpell)
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .height(2 * SPELL_SIZE.dp),
            columns = GridCells.FixedSize(SPELL_SIZE.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalArrangement = Arrangement.SpaceEvenly,
            userScrollEnabled = false,
        ) {
            items(
                items = currentGameState.loot.spells,
                key = { it.id.hashCode() }) { spell ->
                Draggable(
                    modifier = Modifier
                        .height(SPELL_SIZE.dp)
                        .width(SPELL_SIZE.dp)
                        .border(1.dp, Color.LightGray)
                        .align(Alignment.Center),
                    dataToDrop = spell,
                ) {
                    SpellView(spell = spell)
                }
            }
        }
    }
}
