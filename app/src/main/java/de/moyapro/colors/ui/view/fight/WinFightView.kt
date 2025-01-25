package de.moyapro.colors.ui.view.fight

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun WinFightView(startLootActivity: () -> Unit) {
    Text("You won the fight")
    Button(onClick = startLootActivity) {
        Text("Prepare next fight")
    }
}
