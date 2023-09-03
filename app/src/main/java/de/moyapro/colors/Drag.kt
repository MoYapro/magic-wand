package de.moyapro.colors

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
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity


private const val TAG = "DRAG"

@Composable
fun <T> DragTarget(
    modifier: Modifier = Modifier,
    dataToDrop: T,
    viewModel: MainViewModel,
    content: @Composable (() -> Unit)
) {

    var localPosition by remember { mutableStateOf(Offset.Zero) }
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
        .pointerInput(Unit) {
            detectDragGestures(onDragStart = {
                viewModel.startDragging()
                currentState.dataToDrop = dataToDrop
                currentState.isDragging = true
                currentState.dragPosition = localPosition + localOffset + it
                currentState.draggableComposable = content
            }, onDrag = { change, dragAmount ->
                change.consumeAllChanges()
                currentState.dragOffset += dragAmount
                localOffset += dragAmount
            }, onDragEnd = {
                viewModel.stopDragging()
                currentState.isDragging = false
                currentState.dragOffset = Offset.Zero
            }, onDragCancel = {
                viewModel.stopDragging()
                currentState.dragOffset = Offset.Zero
                currentState.isDragging = false
            })
        }) {
        content()
    }
}
