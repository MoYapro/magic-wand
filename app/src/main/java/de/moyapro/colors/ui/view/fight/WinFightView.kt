package de.moyapro.colors.ui.view.fight

import androidx.compose.material3.*
import androidx.compose.runtime.*

@Composable
fun WinFightView(startLootActivity: () -> Unit) {
    Text("You won the fight")
    Button(onClick = startLootActivity) {
        Text("Prepare next fight")
    }
}
