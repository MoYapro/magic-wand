package de.moyapro.colors.ui.view.dragdrop

import android.util.Log
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.actions.NoOp
import de.moyapro.colors.util.castOrNull


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
