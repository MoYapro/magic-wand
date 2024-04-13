package de.moyapro.colors

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import de.moyapro.colors.game.GameViewModel
import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.takeTwo.Wand
import de.moyapro.colors.util.SPELL_SIZE


@Composable
fun LootWandsView(
    wands: List<Wand>,
    mainViewModel: MainViewModel,
    currentGameState: MyGameState,
    addAction: (GameAction) -> GameViewModel,
) {
    if (wands.size >= 1) {
        LazyRow(
            modifier = Modifier
                .fillMaxSize()
                .border(1.dp, Color.LightGray)
        ) {
            items(
                items = wands,
                key = { wand -> wand.id.hashCode() }) { wand ->
                Draggable(dataToDrop = wand, mainViewModel = mainViewModel) {
                    WandView(
                        modifier = Modifier.width(3 * SPELL_SIZE.dp),
                        wand = wand,
                        addAction = addAction,
                        currentGameState = currentGameState
                    )
                }
            }
        }


    } else {
        Text("Win fights to get wands")
    }
}
