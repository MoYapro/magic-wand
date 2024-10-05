package de.moyapro.colors.ui.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.*
import de.moyapro.colors.game.generators.*

@Composable
@Preview
fun PreviewWandsView() {
    Box {
        WandsView(currentGameState = Initializer.createInitialGameState()) {}
    }
}