package de.moyapro.colors

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.takeTwo.*
import de.moyapro.colors.util.*

@Composable
fun EmptyWandSlot(
    addAction: (GameAction) -> GameViewModel,
    currentGameState: MyGameState,
) {
    DropZone(
        modifier = Modifier
            .border(BorderStroke(1.dp, Color.LightGray))
            .fillMaxSize(),
        condition = { state, dropData -> dropData != null && dropData is Wand && state.wands.none { it.id == dropData.id } },
        currentGameState = currentGameState,
    )
    { isInBound: Boolean, droppedWand: Any?, hoveredWand: Any? ->
        val useHoveredWand: Wand? = castOrNull(hoveredWand)
        val useDroppedWand: Wand? = castOrNull(droppedWand)
        if (null != useDroppedWand) addAction(
            CombinedAction(
                AddWandAction(useDroppedWand),
                RemoveWandFromLootAction(useDroppedWand)
            )
        )
        Box(
            modifier = Modifier
                .height(4 * SPELL_SIZE.dp)
                .width(2 * SPELL_SIZE.dp)
                .background(if (isInBound) Color.Green.copy(DROP_ZONE_ALPHA) else Color.Transparent)
        ) {
            if (isInBound && useHoveredWand != null) {
                WandEditView(
                    modifier = Modifier.alpha(DROP_ZONE_ALPHA),
                    currentGameState = currentGameState,
                    wand = useHoveredWand,
                    addAction = addAction,
                )
            } else {
                Text("Place wand here")
            }
        }
    }
}
