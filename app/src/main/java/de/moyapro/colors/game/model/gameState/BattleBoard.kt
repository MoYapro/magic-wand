package de.moyapro.colors.game.model.gameState

import de.moyapro.colors.game.enemy.Enemy
import de.moyapro.colors.game.model.FieldId

data class BattleBoard(
    val fields: List<Field>,
) {

    init {
        require(fields.size == 15) { "BattleBoard must have 15 fields" }
        fields.forEachIndexed { index, field -> require(index.toShort() == field.id.id) { "FieldId must match its index" } }
    }

    fun getEnemies(): List<Enemy> = fields.mapNotNull(Field::enemy)
    fun mapEnemies(modifierFunction: (Enemy) -> Enemy?) = this.copy(fields = fields.map { field ->
        if (field.enemy == null) field
        else field.copy(enemy = modifierFunction(field.enemy))
    })

    operator fun get(id: FieldId): Enemy? {
        return fields[id.id.toInt()].enemy

    }
}
