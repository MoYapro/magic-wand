package de.moyapro.colors.ui.view.fight

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import de.moyapro.colors.game.model.*

@Composable
fun StatusBar(currentState: MyGameState) {
    Row {
        Text("Turn: ${currentState.currentTurn + 1}")
    }

}
