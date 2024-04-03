package de.moyapro.colors

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.moyapro.colors.game.GameViewModel
import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.game.actions.GameAction

@Composable
fun WandsEditView(
    modifier: Modifier = Modifier,
    currentGameState: MyGameState,
    mainViewModel: MainViewModel,
    addAction: (GameAction) -> GameViewModel,
) {
    val wands = currentGameState.wands

    Row(modifier = modifier) {

        if (wands.size > 0) WandEditView(
            modifier = Modifier
                .fillMaxWidth(1f / 3f)
                .fillMaxHeight(),
            currentGameState = currentGameState,
            mainViewModel = mainViewModel,
            addAction = addAction,
            wand = wands[0]
        ) else {
            EmptyWandSlot(addAction = addAction, currentGameState = currentGameState)
        }
        if (wands.size > 1) WandEditView(
            modifier = Modifier.fillMaxWidth(1f / 2f),
            currentGameState = currentGameState,
            mainViewModel = mainViewModel,
            addAction = addAction,
            wand = wands[1]
        ) else {
            EmptyWandSlot(addAction = addAction, currentGameState = currentGameState)
        }
        if (wands.size > 2) WandEditView(
            modifier = Modifier.fillMaxWidth(1f),
            currentGameState = currentGameState,
            mainViewModel = mainViewModel,
            addAction = addAction,
            wand = wands[2]
        ) else {
            EmptyWandSlot(addAction = addAction, currentGameState = currentGameState)
        }
    }
}
