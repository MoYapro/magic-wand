package de.moyapro.colors.game.effect

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.rounded.Fireplace
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

enum class Effect(val icon: ImageVector, val color: Color) {
    WET(Icons.Filled.WaterDrop, Color(45, 153, 207)),
    BURNING(Icons.Rounded.Fireplace, Color.Red),
    ELECTRIFIED(Icons.Filled.Bolt, Color.Yellow)
}