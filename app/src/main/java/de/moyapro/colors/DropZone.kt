package de.moyapro.colors

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned


private const val TAG = "DROP_ITEM"

@Composable
fun <T> DropZone(
    modifier: Modifier = Modifier,
    content: @Composable() (BoxScope.(isInBound: Boolean, dropData: T?, hoverData: T?) -> Unit)
) {

    val dragInfo = LocalDragTargetInfo.current
    val dragPosition = dragInfo.dragPosition
    val dragOffset = dragInfo.dragOffset
    var isCurrentDropTarget by remember {
        mutableStateOf(false)
    }

    Box(modifier = modifier.onGloballyPositioned {
        it.boundsInWindow().let { rect ->
            isCurrentDropTarget = rect.contains(dragPosition + dragOffset)
        }
    }) {
        when {
            !isCurrentDropTarget -> content(false, null, null)
            isCurrentDropTarget && dragInfo.isDragging -> content(
                true,
                null,
                dragInfo.dataToDrop as T?
            )

            isCurrentDropTarget && !dragInfo.isDragging -> content(
                true,
                dragInfo.dataToDrop as T?,
                dragInfo.dataToDrop as T?,
            )

            else -> throw IllegalStateException("unknown drop state")
        }
    }
}
