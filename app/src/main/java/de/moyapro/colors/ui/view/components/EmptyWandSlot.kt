package de.moyapro.colors.ui.view.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.actions.stash.AddWandAction
import de.moyapro.colors.game.model.MageId
import de.moyapro.colors.game.model.Wand
import de.moyapro.colors.game.model.gameState.GameState
import de.moyapro.colors.ui.view.dragdrop.DropZone
import de.moyapro.colors.ui.view.stash.WandEditView
import de.moyapro.colors.util.SPELL_SIZE
import de.moyapro.colors.util.castOrNull

@Composable
fun EmptyWandSlot(
    mageId: MageId,
    addAction: (GameAction) -> Unit,
    currentGameState: GameState,
) {
    DropZone<Wand>(
        modifier = Modifier
            .border(BorderStroke(1.dp, Color.LightGray))
            .width(2 * SPELL_SIZE.dp)
            .fillMaxHeight(),
        condition = { _, newWand -> newWand.mageId != mageId },
        addAction = addAction,
        currentGameState = currentGameState,
        onDropAction = { droppedWand -> AddWandAction(droppedWand, mageId) },
    )
    { modifier: Modifier, isInBound: Boolean, hoveredWand: Wand? ->
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
                    Text("Place wand here")
                }
            }
        }
    }
}
