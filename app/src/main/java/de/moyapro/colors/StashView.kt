package de.moyapro.colors

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.moyapro.colors.game.GameViewModel
import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.actions.PlaceSpellInStashAction
import de.moyapro.colors.util.DROP_ZONE_ALPHA
import de.moyapro.colors.util.SPELL_SIZE
import de.moyapro.colors.wand.Spell

@Composable
fun StashView(
    modifier: Modifier,
    currentGameState: MyGameState,
    mainViewModel: MainViewModel,
    addAction: (GameAction) -> GameViewModel,
) {
    DropZone<Spell>(
        modifier = modifier.border(BorderStroke(1.dp, Color.LightGray)),
        condition = { state, dragData -> !state.spellsInStash.contains(dragData) },
        currentGameState = currentGameState,
        { isInBound: Boolean, droppedSpell: Spell?, hoveredSpell: Spell? ->
            val canDrop: Boolean =
                isInBound && !currentGameState.spellsInStash.contains(
                    hoveredSpell
                )
            if (canDrop && null != droppedSpell) {
                LaunchedEffect(key1 = droppedSpell) {
                    addAction(PlaceSpellInStashAction(droppedSpell.id))
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
        },
    )
}