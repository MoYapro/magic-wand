package de.moyapro.colors.ui.view.fight

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.*

@Composable
fun HealthView(health: Int = 21) {
    Row {
        Text(text = "♥", color = Color.Red)
        Text(" x $health", color = Color.Green)
    }
}
