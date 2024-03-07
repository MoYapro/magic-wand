package de.moyapro.colors

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import de.moyapro.colors.game.MyGameState

@Composable
fun StatusBar(currentState: MyGameState) {
    Row {
        Text("Turn: ${currentState.currentTurn + 1}")
    }

}
