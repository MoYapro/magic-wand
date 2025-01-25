package de.moyapro.colors.game.model.gameState

import de.moyapro.colors.game.enemy.Enemy
import de.moyapro.colors.game.model.FieldId
import de.moyapro.colors.game.model.interfaces.HasId

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
