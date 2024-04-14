package de.moyapro.colors

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.layout.*
import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.*


private const val TAG = "DROP_ITEM"

@Composable
inline fun <reified T : Any> DropZone(
    modifier: Modifier = Modifier,
    noinline condition: (gameState: MyGameState, dropData: T?) -> Boolean = { _, _ -> true },
    currentGameState: MyGameState,
    addAction: (GameAction) -> GameViewModel,
    noinline onDropAction: ((T) -> GameAction)? = null,
    content: @Composable() (BoxScope.(isInBound: Boolean, dropData: Any?, hoverData: Any?) -> Unit),
) {
    val dragInfo = LocalDragTargetInfo.current
    val dragPosition = dragInfo.dragPosition
    val dragOffset = dragInfo.dragOffset
    var isCurrentDropTarget by remember { mutableStateOf(false) }
    val isHovering = isCurrentDropTarget && dragInfo.isDragging
    val isDropping = isCurrentDropTarget && !dragInfo.isDragging

    Box(modifier = modifier.onGloballyPositioned {
        it.boundsInWindow().let { rect ->
            isCurrentDropTarget = rect.contains(dragPosition + dragOffset) && dragInfo.dataToDrop is T && condition(
                currentGameState,
                dragInfo.dataToDrop as T
            )
        }
    }) {
        when {
            !isCurrentDropTarget -> content(false, null, null)
            isHovering -> content(true, null, dragInfo.dataToDrop)
            isDropping -> {
                addAction(
                    CombinedAction(
                        dragInfo.onDropAction,
                        onDropAction?.invoke(dragInfo.dataToDrop as T)
                    )
                )
                content(true, dragInfo.dataToDrop, dragInfo.dataToDrop)
            }

            else -> throw IllegalStateException("unknown drop state")
        }
    }
}
