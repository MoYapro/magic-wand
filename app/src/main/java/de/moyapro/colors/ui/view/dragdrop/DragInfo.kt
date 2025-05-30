package de.moyapro.colors.ui.view.dragdrop

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.actions.NoOp

val LocalDragTargetInfo = compositionLocalOf { DragInfo<Any>() }


class DragInfo<T> {
    var onDropDidReplaceAction: (T) -> GameAction by mutableStateOf({ NoOp() })
    var dragStartPosition: Offset by mutableStateOf(Offset.Zero)
    var isDragging: Boolean by mutableStateOf(false)
    var dragPosition: Offset by mutableStateOf(Offset.Zero)
    var dragOffset: Offset by mutableStateOf(Offset.Zero)
    var dataToDrop: Any? by mutableStateOf(null)
    var onDropAction: GameAction? by mutableStateOf(null)

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
//        this.dataToDrop = null
//        this.onDropAction = null
//        this.onDropDidReplaceAction = NoOp()
    }
}
