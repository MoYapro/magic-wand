package de.moyapro.colors

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import de.moyapro.colors.game.GameViewModel
import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.takeTwo.Wand
import de.moyapro.colors.util.DROP_ZONE_ALPHA
import de.moyapro.colors.util.SPELL_SIZE
import de.moyapro.colors.util.castOrNull

@Composable
fun EmptyWandSlot(
    addAction: (GameAction) -> GameViewModel,
    currentGameState: MyGameState,
) {
    DropZone(
        modifier = Modifier
            .border(BorderStroke(1.dp, Color.LightGray))
            .fillMaxSize(),
        condition = { _, dropData -> dropData != null && dropData is Wand },
        currentGameState = currentGameState,
    )
    { isInBound: Boolean, droppedWand: Any?, hoveredWand: Any? ->
        val useHoveredWand: Wand? = castOrNull(hoveredWand)
        Box(
            modifier = Modifier
                .height(4 * SPELL_SIZE.dp)
                .width(2 * SPELL_SIZE.dp)
                .background(if (isInBound) Color.Green.copy(DROP_ZONE_ALPHA) else Color.Transparent)
        ) {
            if (isInBound && useHoveredWand != null)
                WandEditView(
                    modifier = Modifier.alpha(DROP_ZONE_ALPHA),
                    currentGameState = currentGameState,
                    mainViewModel = MainViewModel(),
                    wand = useHoveredWand,
                    addAction = addAction,
                )
        }
    }
}