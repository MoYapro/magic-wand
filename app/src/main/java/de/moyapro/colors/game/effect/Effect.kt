package de.moyapro.colors.game.effect

import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.*

enum class Effect(val icon: ImageVector, val color: Color) {
    WET(Icons.Filled.WaterDrop, Color(45, 153, 207)),
    BURNING(Icons.Rounded.Fireplace, Color.Red),
    ELECTRIFIED(Icons.Filled.Bolt, Color.Yellow)
}