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

    var x by remember { mutableStateOf(0f) }
    var y by remember { mutableStateOf(0f) }

    // The offsets that is always update, that place the component to be dragged
    val offsetX = with(LocalDensity.current) {
        x.toDp()
    }
    val offsetY = with(LocalDensity.current) {
        y.toDp()
    }


    Box(modifier = modifier
        .offset(offsetX, offsetY)
        .pointerInput(Unit) {
            detectDragGestures(onDragStart = {
//                currentOffset = it
            }, onDrag = { change, dragAmount ->
                change.consumeAllChanges()

                x += dragAmount.x
                y += dragAmount.y
            }, onDragEnd = {
            }, onDragCancel = {
            })
        }) {
        content()
    }
}
