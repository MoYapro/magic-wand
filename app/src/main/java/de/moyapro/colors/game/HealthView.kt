package de.moyapro.colors.game

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.*

@Composable
fun HealthView(health: Int) {
    Row {
        Text("$health x ")
        Text(text = "♥", color = Color.Red)
    }
}
