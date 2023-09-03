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
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity


private const val TAG = "DRAG"

@Composable
fun <T> DragTarget(
    modifier: Modifier = Modifier,
    dataToDrop: T,
    viewModel: MainViewModel,
    content: @Composable (() -> Unit)
) {

    var globalStartPosition by remember { mutableStateOf(Offset.Zero) }
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
        .onGloballyPositioned {
            if (globalStartPosition == Offset.Zero)
                globalStartPosition = it.localToWindow(
                    Offset.Zero
                )
            Log.d(TAG, "set global start position: $globalStartPosition")
        }
        .pointerInput(Unit) {
            detectDragGestures(onDragStart = {
                viewModel.startDragging()
                currentState.dataToDrop = dataToDrop
                currentState.isDragging = true
                currentState.dragPosition = globalStartPosition + it
                val sumn = localPosition + localOffset + it
                Log.d(
                    TAG,
                    "drag start offset: $it, localPosition: $localPosition, localOffset: $localOffset, sumn: $sumn"
                )
                currentState.draggableComposable = content
            }, onDrag = { change, dragAmount ->
                change.consumeAllChanges()
                localOffset += dragAmount
                currentState.dragPosition = globalStartPosition + localOffset
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
