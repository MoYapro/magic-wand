package de.moyapro.colors.game.model.gameState

import de.moyapro.colors.game.enemy.*

data class BattleBoard(
    val fields: List<Field>, // TODO Place grid here
) {
    fun getEnemies(): List<Enemy> = fields.mapNotNull(Field::enemy)
    fun mapEnemies(mf: (Enemy) -> Enemy?) = this.copy(fields = fields.map { field ->
        if (field.enemy == null) field
        else field.copy(enemy = mf(field.enemy))
    })
}
