package de.moyapro.colors

import androidx.compose.runtime.*
import androidx.compose.ui.geometry.*
import de.moyapro.colors.game.actions.*

val LocalDragTargetInfo = compositionLocalOf { DragInfo() }


class DragInfo {
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
    }
}
