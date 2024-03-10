package de.moyapro.colors.game

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun HealthView(health: Int) {
    Row {
        Text(health.toString())
        repeat(health) {
            Text(
                text = "O",
                color = Color.Red
            )
        }
    }
}
