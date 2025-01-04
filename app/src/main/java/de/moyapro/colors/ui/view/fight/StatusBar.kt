package de.moyapro.colors.ui.view.fight

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import de.moyapro.colors.game.model.gameState.GameState

@Composable
fun StatusBar(currentState: GameState) {
    Row {
        Text("Turn: ${currentState.currentFight.currentTurn + 1}")
    }

}
