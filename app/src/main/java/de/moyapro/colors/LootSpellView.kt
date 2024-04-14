package de.moyapro.colors

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

@Composable
fun LootSpellsView(modifier: Modifier = Modifier, spells: List<Spell>, currentGameState: MyGameState, addAction: (GameAction) -> GameViewModel) {
    DropZone(
        modifier = modifier.border(BorderStroke(1.dp, Color.LightGray)),
        condition = { state, dragData -> !state.loot.spells.contains(dragData) },
        currentGameState = currentGameState,
        { isInBound: Boolean, droppedSpell: Any?, hoveredSpell: Any? ->
            val useDroppedSpell: Spell? = castOrNull(droppedSpell)
            val useHoveredSpell: Spell? = castOrNull(hoveredSpell)
            val canDrop: Boolean =
                isInBound && !currentGameState.loot.spells.contains(
                    useHoveredSpell
                )
            if (canDrop && null != useDroppedSpell) {
                LaunchedEffect(key1 = useDroppedSpell) {
                    TODO()
                }
            }
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2 * SPELL_SIZE.dp)
                    .background(color = if (canDrop) Color.Green.copy(alpha = DROP_ZONE_ALPHA) else Color.Transparent),
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
    )
}
