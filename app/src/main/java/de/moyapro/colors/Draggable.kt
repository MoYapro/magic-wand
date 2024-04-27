package de.moyapro.colors

import android.util.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.util.*


private const val TAG = "DRAGABLE"

@Composable
fun <T : Any> Draggable(
    modifier: Modifier = Modifier,
    dataToDrop: T,
    onDropAction: GameAction? = null,
    onDropDidReplaceAction: (T) -> GameAction = { NoOp() },
    requireLongPress: Boolean = false,
    content: @Composable ((dragedData: T, isDragging: Boolean) -> Unit),
) {
    var thisIsDraged by remember { mutableStateOf(false) }
    var globalStartPosition by remember { mutableStateOf(Offset.Zero) }
    val localPosition by remember { mutableStateOf(Offset.Zero) }
    var localOffset by remember { mutableStateOf(Offset.Zero) }
    val currentState: DragInfo<T> = castOrNull(LocalDragTargetInfo.current)!!
    // The offsets that is always update, that place the component to be dragged
    val offsetX = with(LocalDensity.current) {
        (localPosition.x + localOffset.x).toDp()
    }
    val offsetY = with(LocalDensity.current) {
        (localPosition.y + localOffset.y).toDp()
    }

    fun onDragEnd(
        currentState: DragInfo<T>,
        updateLocalOffset: (Offset) -> Unit,
        setDraggableState: (Boolean) -> Unit,

        ) {
        currentState.reset()
        setDraggableState(false)
        updateLocalOffset(Offset.Zero)
    }


    val updateLocalOffset: (Offset) -> Unit = { newOffset -> localOffset = newOffset }


    fun onDrag(
        currentState: DragInfo<T>,
        change: PointerInputChange,
        dragAmount: Offset,
        localOffset: Offset,
        updateLocalOffset: (Offset) -> Unit,
    ) {
        change.consume()
        updateLocalOffset(localOffset + dragAmount)
        currentState.dragPosition = currentState.dragStartPosition + localOffset
    }

    fun onDragStart(
        currentState: DragInfo<T>,
        currentDragOffset: Offset,
        globalStartPosition: Offset,
        dataToDrop: T,
        onDropAction: GameAction?,
        onDropDidReplaceAction: (T) -> GameAction,
        setDraggableState: (Boolean) -> Unit,
    ) {
        Log.d("DRAG_START", "Start drag with $dataToDrop")
        setDraggableState(true)
        currentState.dragStartPosition = globalStartPosition
        currentState.dataToDrop = dataToDrop
        currentState.onDropDidReplaceAction = onDropDidReplaceAction
        currentState.onDropAction = onDropAction
        currentState.isDragging = true
        currentState.dragPosition = globalStartPosition + currentDragOffset
    }

    val setDragState = { newDragState: Boolean -> thisIsDraged = newDragState }
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
                        onDragStart(currentState, currentDragOffset, globalStartPosition, dataToDrop, onDropAction, onDropDidReplaceAction, setDragState)
                    },
                    onDrag = { change, dragAmount ->
                        onDrag(currentState, change, dragAmount, localOffset, updateLocalOffset)
                    },
                    onDragEnd = { onDragEnd(currentState, updateLocalOffset, setDragState) },
                    onDragCancel = { onDragEnd(currentState, updateLocalOffset, setDragState) }
                )
            } else {
                detectDragGestures(
                    onDragStart = { currentDragOffset ->
                        onDragStart(currentState, currentDragOffset, globalStartPosition, dataToDrop, onDropAction, onDropDidReplaceAction, setDragState)
                    },
                    onDrag = { change, dragAmount ->
                        onDrag(currentState, change, dragAmount, localOffset, updateLocalOffset)
                    },
                    onDragEnd = { onDragEnd(currentState, updateLocalOffset, setDragState) },
                    onDragCancel = { onDragEnd(currentState, updateLocalOffset, setDragState) }
                )
            }
        }) {
        content(dataToDrop, thisIsDraged)
    }
}
