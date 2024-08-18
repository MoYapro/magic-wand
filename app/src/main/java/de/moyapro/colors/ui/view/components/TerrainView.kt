package de.moyapro.colors.ui.view.components

import androidx.compose.material3.*
import androidx.compose.runtime.*
import de.moyapro.colors.game.model.gameState.*

@Composable
fun TerrainView(terrain: Terrain) {
    Text(text = terrain.name)
}