package de.moyapro.colors.ui.view.loot

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import de.moyapro.colors.game.model.*

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