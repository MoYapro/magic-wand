package de.moyapro.colors.game.model.gameState

import de.moyapro.colors.game.enemy.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.interfaces.*

data class Field(
    override val id: FieldId,
    val enemy: Enemy?,
    val terrain: Terrain,
    val width: Int = 1,
    val height: Int = 1,
    val showTarget: Boolean = false,
) : HasId<FieldId> {
    fun hasNoEnemy(): Boolean = enemy == null
}
