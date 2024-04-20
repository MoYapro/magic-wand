package de.moyapro.colors

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.*
import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.util.*


@Composable
inline fun <reified T : Any> DropZone(
    modifier: Modifier = Modifier,
    noinline condition: (gameState: MyGameState, dropData: T) -> Boolean = { _, _ -> true },
    currentGameState: MyGameState,
    addAction: (GameAction) -> GameViewModel,
    noinline onDropAction: ((T) -> GameAction)? = null,
    content: @Composable (BoxScope.(modifier: Modifier, isInBound: Boolean, dropData: Any?, hoverData: Any?) -> Unit),
) {
    val dragInfo = LocalDragTargetInfo.current
    val dragPosition = dragInfo.dragPosition
    val dragOffset = dragInfo.dragOffset
    var isCurrentDropTarget by remember { mutableStateOf(false) }
    val isHovering = isCurrentDropTarget && dragInfo.isDragging
    val isDropping = isCurrentDropTarget && !dragInfo.isDragging

    Box(
        modifier = modifier
            .onGloballyPositioned {
        it.boundsInWindow().let { rect ->
            val castedDropData: T? = castOrNull(dragInfo.dataToDrop)
            isCurrentDropTarget = rect.contains(dragPosition + dragOffset) && null != castedDropData && condition(
                currentGameState,
                castedDropData
            )
        }
    }) {
        when {
            !isCurrentDropTarget -> content(Modifier, false, null, null)
            isHovering -> content(Modifier.background(color = Color.Green.copy(alpha = DROP_ZONE_ALPHA)), true, null, dragInfo.dataToDrop)
            isDropping -> {
                addAction(
                    CombinedAction(
                        dragInfo.onDropAction,
                        onDropAction?.invoke(castOrNull(dragInfo.dataToDrop) ?: throw IllegalStateException("Tried to drop null data"))
                    )
                )
                content(Modifier, true, dragInfo.dataToDrop, dragInfo.dataToDrop)
                dragInfo.dataToDrop = null
                dragInfo.onDropAction = null
            }

            else -> throw IllegalStateException("unknown drop state")
        }
    }
}
