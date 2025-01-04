package de.moyapro.colors.ui.view.fight

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun HealthView(health: Int = 21) {
    Row {
        Text(text = "♥", color = Color.Red)
        Text(" x $health", color = Color.Green)
    }
}
