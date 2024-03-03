package de.moyapro.colors

import android.util.Log
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity


private const val TAG = "DRAG"
@Composable
fun <T> Draggable(
    modifier: Modifier = Modifier,
    dataToDrop: T,
    viewModel: MainViewModel,
    content: @Composable ((T) -> Unit)
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


    Box(modifier = modifier
        .offset(offsetX, offsetY)
        .onGloballyPositioned {
            if (globalStartPosition == Offset.Zero)
                globalStartPosition = it.localToWindow(
                    Offset.Zero
                )
        }
        .pointerInput(Unit) {
            detectDragGestures(onDragStart = { currentDragOffset ->
                currentState.dragStartPosition = Offset.Zero
                currentState.dataToDrop = dataToDrop
                currentState.isDragging = true
                currentState.dragPosition = globalStartPosition + currentDragOffset
                val sum = localPosition + localOffset + currentDragOffset
                Log.d(
                    TAG,
                    "drag start offset: $currentDragOffset, localPosition: $localPosition, localOffset: $localOffset, sum: $sum, startPosition: ${currentState.dragStartPosition}, person: ${currentState.dataToDrop}"
                )
            }, onDrag = { change, dragAmount ->
                change.consume()
                localOffset += dragAmount
                currentState.dragPosition = globalStartPosition + localOffset
            }, onDragEnd = {
                Log.d(
                    TAG,
                    "drag end , localPosition: $localPosition, localOffset: $localOffset, startPosition: ${currentState.dragStartPosition}"
                )
                viewModel.stopDragging()
                currentState.isDragging = false
                currentState.dragOffset = Offset.Zero
                currentState.dragPosition = Offset.Zero
                localOffset = Offset.Zero
            }, onDragCancel = {
                Log.d(
                    TAG,
                    "drag cancel , localPosition: $localPosition, localOffset: $localOffset, startPosition: ${currentState.dragStartPosition}"
                )
                viewModel.stopDragging()
                currentState.isDragging = false
                currentState.dragOffset = Offset.Zero
                currentState.dragPosition = Offset.Zero
            })
        }) {
        content(dataToDrop)
    }
}
