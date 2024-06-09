package de.moyapro.colors.ui.view.fight

import androidx.compose.material3.*
import androidx.compose.runtime.*

@Composable
fun LostFightView(startMainActivity: () -> Unit) {
    Text("You lost the fight")
    Button(onClick = startMainActivity) {
        Text("Back to main menu")
    }
}
