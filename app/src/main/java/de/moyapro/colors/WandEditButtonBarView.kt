package de.moyapro.colors

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.moyapro.colors.game.MyGameState

@Composable
fun WandEditButtonBarView(
    backToMainMenu: () -> Unit,
    saveWands: (MyGameState) -> Unit,
    currentGameState: MyGameState,
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Button(onClick = { TODO() }) {
            Text("Undo")
        }
        Button(onClick = { TODO() }) {
            Text("discard changes")
        }
        Button(onClick = {
            saveWands(currentGameState)
            backToMainMenu()
        }) {
            Text("save wands")
        }
    }
}