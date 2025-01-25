package de.moyapro.colors.ui.view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import de.moyapro.colors.game.generators.Initializer

@Composable
@Preview
fun PreviewWandsView() {
    Box {
        WandsView(currentGameState = Initializer.createInitialGameState()) {}
    }
}