package de.moyapro.colors

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.unit.*

internal val LocalDragTargetInfo = compositionLocalOf { DragTargetInfo() }

@Composable
fun DragAndDropContainer(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val state = remember { DragTargetInfo() }
    CompositionLocalProvider(
        LocalDragTargetInfo provides state
    ) {
        Box(modifier = modifier.fillMaxSize())
        {
            content()
            if (state.isDragging) {
                var targetSize by remember {
                    mutableStateOf(IntSize.Zero)
                }
                Box(modifier = Modifier
                    .graphicsLayer {
                        val offset = (state.dragPosition + state.dragOffset)
                        scaleX = 1.0f
                        scaleY = 1.0f
                        alpha = if (targetSize == IntSize.Zero) 0f else .9f
                        translationX = offset.x.minus(targetSize.width / 2)
                        translationY = offset.y.minus(targetSize.height / 2)
                    }
                    .onGloballyPositioned {
                        targetSize = it.size
                    }
                ) {
                    var pos by remember {
                        mutableStateOf(Offset.Zero)
                    }
                    Box(
                        modifier = Modifier
                            .onGloballyPositioned { pos = it.positionInRoot() }
                            .size(Dp(75f))
                            .clip(RoundedCornerShape(15.dp))
                            .shadow(5.dp, RoundedCornerShape(15.dp))
                            .background(Color.White, RoundedCornerShape(15.dp)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Column {
                            Text("""${pos.x.toInt()} x ${pos.y.toInt()}
                                |${state.dragPosition.x.toInt()} x ${state.dragPosition.y.toInt()}
                            """.trimMargin())
                        }
                    }

                }
            }
        }
    }
}


internal class DragTargetInfo {
    var dragStartPosition: Offset by mutableStateOf(Offset.Zero)
    var isDragging: Boolean by mutableStateOf(false)
    var dragPosition by mutableStateOf(Offset.Zero)
    var dragOffset by mutableStateOf(Offset.Zero)
    var dataToDrop by mutableStateOf<Any?>(null)

    override fun toString(): String {
        return """
            dragPosition: ${dragPosition.x} x ${dragPosition.y}
            dragOffset: $dragOffset
//            isDragging: $isDragging
//            dataToDrop: $dataToDrop
        """.trimIndent()
    }

    fun reset() {
        this.isDragging = false
        this.dragOffset = Offset.Zero
        this.dragPosition = Offset.Zero
    }
}
