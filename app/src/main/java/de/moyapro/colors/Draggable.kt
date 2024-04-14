package de.moyapro.colors

import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.*
import de.moyapro.colors.game.actions.*


private const val TAG = "DRAGABLE"

@Composable
fun <T> Draggable(
    modifier: Modifier = Modifier,
    dataToDrop: T,
    onDropAction: GameAction? = null,
    requireLongPress: Boolean = false,
    content: @Composable ((T) -> Unit),
) {
    var globalStartPosition by remember { mutableStateOf(Offset.Zero) }
    val localPosition by remember { mutableStateOf(Offset.Zero) }
    var localOffset by remember { mutableStateOf(Offset.Zero) }
    val currentState = LocalDragTargetInfo.current
    // The offsets that is always update, that place the component to be dragged
    val offsetX = with(LocalDensity.current) {
        (localPosition.x + localOffset.x).toDp()
    }
    val offsetY = with(LocalDensity.current) {
        (localPosition.y + localOffset.y).toDp()
    }


    val updateLocalOffset: (Offset) -> Unit = { newOffset -> localOffset = newOffset }
    Box(modifier = modifier
        .offset(offsetX, offsetY)
        .onGloballyPositioned {
            if (globalStartPosition == Offset.Zero)
                globalStartPosition = it.localToWindow(
                    Offset.Zero
                )
        }
        .pointerInput(Unit) {
            if (requireLongPress) {
                detectDragGesturesAfterLongPress(
                    onDragStart = { currentDragOffset ->
                        onDragStart(currentState, currentDragOffset, globalStartPosition, dataToDrop, onDropAction)
                    },
                    onDrag = { change, dragAmount ->
                        onDrag(currentState, change, dragAmount, localOffset, updateLocalOffset)
                    },
                    onDragEnd = { onDragEnd(currentState, updateLocalOffset) },
                    onDragCancel = { onDragEnd(currentState, updateLocalOffset) }
                )
            } else {
                detectDragGestures(
                    onDragStart = { currentDragOffset ->
                        onDragStart(currentState, currentDragOffset, globalStartPosition, dataToDrop, onDropAction)
                    },
                    onDrag = { change, dragAmount ->
                        onDrag(currentState, change, dragAmount, localOffset, updateLocalOffset)
                    },
                    onDragEnd = { onDragEnd(currentState, updateLocalOffset) },
                    onDragCancel = { onDragEnd(currentState, updateLocalOffset) }
                )
            }
        }) {
        content(dataToDrop)
    }
}

private fun onDragEnd(currentState: DragInfo, updateLocalOffset: (Offset) -> Unit) {
    currentState.reset()
    updateLocalOffset(Offset.Zero)
}

private fun onDrag(
    currentState: DragInfo,
    change: PointerInputChange,
    dragAmount: Offset,
    localOffset: Offset,
    updateLocalOffset: (Offset) -> Unit,
) {
    change.consume()
    updateLocalOffset(localOffset + dragAmount)
    currentState.dragPosition = currentState.dragStartPosition + localOffset
}

private fun <T> onDragStart(
    currentState: DragInfo,
    currentDragOffset: Offset,
    globalStartPosition: Offset,
    dataToDrop: T,
    onDropAction: GameAction?,
) {
    currentState.dragStartPosition = globalStartPosition
    currentState.dataToDrop = dataToDrop
    currentState.onDropAction = onDropAction
    currentState.isDragging = true
    currentState.dragPosition = globalStartPosition + currentDragOffset
}
