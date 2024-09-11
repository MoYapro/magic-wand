package de.moyapro.colors.game.effect

import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.*

enum class Effect(val icon: ImageVector, val color: Color) {
    WET(Icons.Filled.LocationOn, Color.Blue),
    BURNING(Icons.Filled.Warning, Color.Red),
    ELECTRIFIED(Icons.Filled.Star, Color.Yellow)
}