package de.moyapro.colors.ui.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import de.moyapro.colors.game.model.Wand
import de.moyapro.colors.game.model.WandId
import de.moyapro.colors.game.model.gameState.GameState
import de.moyapro.colors.util.SPELL_SIZE

@Composable
fun WandGrid(
    wands: List<Wand>,
    highlightedWands: List<WandId>,
    clickWandAction: (Wand) -> Unit,
    currentGameState: GameState,
) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth()
            .height(2 * SPELL_SIZE.dp),
        columns = GridCells.FixedSize(SPELL_SIZE.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalArrangement = Arrangement.SpaceEvenly,
        userScrollEnabled = false,
    ) {
        items(items = wands, key = { wand -> wand.id.hashCode() }) { wand ->
            WandView(
                modifier = Modifier.background(if (highlightedWands.contains(wand.id)) Color.Cyan else Color.Transparent),
                wand = wand,
                addAction = {},
                currentGameState = currentGameState,
                clickAction = clickWandAction
            )
        }
    }
}