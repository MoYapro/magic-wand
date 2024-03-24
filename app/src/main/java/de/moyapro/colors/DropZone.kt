package de.moyapro.colors

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import de.moyapro.colors.game.GameViewModel
import de.moyapro.colors.game.MyGameState


private const val TAG = "DROP_ITEM"

@Composable
fun <T> DropZone(
    modifier: Modifier = Modifier,
    condition: (gameState: MyGameState, dropData: T?) -> Boolean = { _, _ -> true},
    gameViewModel: GameViewModel,
    content: @Composable() (BoxScope.(isInBound: Boolean, dropData: T?, hoverData: T?) -> Unit)
) {
    val currentGameStateResult: Result<MyGameState> by gameViewModel.uiState.collectAsState()
    val currentGameState: MyGameState = currentGameStateResult.getOrElse {
        Toast.makeText(LocalContext.current, it.message, Toast.LENGTH_LONG).show()
        MyGameState(emptyList(), emptyList(), emptyList(), 0, emptyList())
    }
    val dragInfo = LocalDragTargetInfo.current
    val dragPosition = dragInfo.dragPosition
    val dragOffset = dragInfo.dragOffset
    var isCurrentDropTarget by remember {
        mutableStateOf(false)
    }

    Box(modifier = modifier.onGloballyPositioned {
        it.boundsInWindow().let { rect ->
            isCurrentDropTarget = rect.contains(dragPosition + dragOffset) && condition(currentGameState, dragInfo.dataToDrop as T?)
        }
    }) {
        when {
            !isCurrentDropTarget -> content(false, null, null)
            isCurrentDropTarget && dragInfo.isDragging -> content(
                true,
                null,
                dragInfo.dataToDrop as T?
            )

            isCurrentDropTarget && !dragInfo.isDragging -> content(
                true,
                dragInfo.dataToDrop as T?,
                dragInfo.dataToDrop as T?,
            )

            else -> throw IllegalStateException("unknown drop state")
        }
    }
}
