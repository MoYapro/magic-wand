package de.moyapro.colors.ui.view.fight

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import de.moyapro.colors.game.model.gameState.*

@Composable
fun StatusBar(currentState: GameState) {
    Row {
        Text("Turn: ${currentState.currentFight.currentTurn + 1}")
    }

}
