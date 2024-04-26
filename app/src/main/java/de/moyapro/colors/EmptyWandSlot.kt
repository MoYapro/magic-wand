package de.moyapro.colors

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.takeTwo.*
import de.moyapro.colors.util.*

@Composable
fun EmptyWandSlot(
    mageId: MageId,
    addAction: (GameAction) -> GameViewModel,
    currentGameState: MyGameState,
) {
    DropZone<Wand>(
        modifier = Modifier
            .border(BorderStroke(1.dp, Color.LightGray))
            .width(2 * SPELL_SIZE.dp)
            .fillMaxHeight(),
        condition = { state, dropData -> state.wands.none { it.id == dropData.id } },
        addAction = addAction,
        currentGameState = currentGameState,
        onDropAction = { droppedWand -> AddWandAction(droppedWand, mageId) },
    )
    { modifier: Modifier, isInBound: Boolean, droppedWand: Any?, hoveredWand: Any? ->
        val useHoveredWand: Wand? = castOrNull(hoveredWand)
        Box(
            modifier = modifier
                .height(4 * SPELL_SIZE.dp)
                .width(2 * SPELL_SIZE.dp)
        ) {
            if (isInBound && useHoveredWand != null) {
                WandEditView(
                    wand = useHoveredWand,
                    currentGameState = currentGameState,
                    addAction = addAction,
                )
            } else {
                Column {
                    Text(mageId.toString())
                    Text("Place wand here")
                }

            }
        }
    }
}
