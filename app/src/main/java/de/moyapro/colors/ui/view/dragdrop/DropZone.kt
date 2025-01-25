package de.moyapro.colors.ui.view.dragdrop

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import de.moyapro.colors.game.actions.CombinedAction
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.actions.NoOp
import de.moyapro.colors.game.model.gameState.GameState
import de.moyapro.colors.util.DROP_ZONE_ALPHA
import de.moyapro.colors.util.castOrNull

@Composable
inline fun <reified T : Any> DropZone(
    modifier: Modifier = Modifier,
    noinline condition: (gameState: GameState, dropData: T) -> Boolean = { _, _ -> true },
    currentGameState: GameState,
    addAction: (GameAction) -> Unit,
    emitData: T? = null,
    noinline onDropAction: ((T) -> GameAction)? = null,
    content: @Composable (BoxScope.(modifier: Modifier, isInBound: Boolean, hoverData: T?) -> Unit),
) {
    val dragInfo: DragInfo<T> = castOrNull(LocalDragTargetInfo.current) ?: throw IllegalStateException("DropInfo type does not fit dropZone type")
    val dragPosition = dragInfo.dragPosition
    val dragOffset = dragInfo.dragOffset
    var isCurrentDropTarget by remember { mutableStateOf(false) }
    val isHovering = isCurrentDropTarget && dragInfo.isDragging
    val isDropping = isCurrentDropTarget && !dragInfo.isDragging

    Box(
        modifier = modifier
            .onGloballyPositioned {
                it.boundsInWindow().let { rect ->
                    val isInBounds = rect.contains(dragPosition + dragOffset)
                    isCurrentDropTarget = if (!isInBounds) {
                        false
                    } else {
                        val castedDropData = castOrNull<T>(dragInfo.dataToDrop)
                        null != castedDropData && condition(currentGameState, castedDropData)
                    }
                }
            }) {
        when {
            !isCurrentDropTarget -> content(Modifier, false, null)
            isHovering -> content(Modifier.background(color = Color.Green.copy(alpha = DROP_ZONE_ALPHA)), true, castOrNull(dragInfo.dataToDrop))
            isDropping -> {
                val castedDropData: T? = castOrNull(dragInfo.dataToDrop)
                addAction(
                    if (null != castedDropData) {
                        dragInfo.dataToDrop = null
                        CombinedAction(
                            dragInfo.onDropAction,
                            onDropAction?.invoke(castedDropData),
                            buildReplaceAction(dragInfo.onDropDidReplaceAction, emitData)
                        )
                    } else {
                        Log.w("DROP_ZONE", "Tried to drop null data")
                        NoOp()
                    }
                )
                content(Modifier, true, castedDropData)
                dragInfo.dataToDrop = null
                dragInfo.onDropAction = null
            }

            else -> throw IllegalStateException("unknown drop state")
        }
    }
}

fun <T> buildReplaceAction(onDropDidReplaceAction: (T) -> GameAction, emitData: T?): GameAction {
    return if (emitData == null) {
        NoOp()
    } else {
        onDropDidReplaceAction(emitData)
    }
}
