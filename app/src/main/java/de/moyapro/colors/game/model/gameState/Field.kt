package de.moyapro.colors.game.model.gameState

import de.moyapro.colors.game.enemy.*

data class Field(
    val enemy: Enemy?,
    val terrain: Terrain,
)
