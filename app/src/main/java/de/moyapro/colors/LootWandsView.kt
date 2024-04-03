package de.moyapro.colors

import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
        val wand = wands[0]
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