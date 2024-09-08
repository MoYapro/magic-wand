package de.moyapro.colors.game.model

import de.moyapro.colors.game.model.accessor.*
import de.moyapro.colors.game.model.gameState.*
import de.moyapro.colors.game.model.interfaces.*
import de.moyapro.colors.util.*

data class Wand(
    override val id: WandId = WandId(),
    val slots: List<Slot> = emptyList(),
    val zapped: Boolean = false,
    val mageId: MageId? = null,
) : HasId<WandId> {
    fun putSpell(slotId: SlotId, spell: Spell): Wand {
        return this.copy(slots = slots.mapIf({ it.id == slotId }, { it.copy(spell = spell) }))
    }

    override fun toString(): String {
        return "Wand($id)"
    }

    fun putMagic(slotId: SlotId, magic: Magic): Result<Wand> {
        var placedMagic = false
        val updatedSlots = slots.map { slot ->
            if (placedMagic || slot.id != slotId) Result.success(slot)
            else {
                placedMagic = true
                slot.putMagic(magic)
            }
        }
        val occuredError = updatedSlots.firstOrNull { it.isFailure }?.exceptionOrNull()
        if (null != occuredError) {
            return Result.failure(occuredError)
        }
        val updatedWand = Result.success(
            this.copy(slots = updatedSlots.map { it.getOrThrow() })
        )
        return if (placedMagic) updatedWand
        else Result.failure(IllegalStateException("Could not place magic. No fitting free slot found."))
    }

    fun affect(battleBoard: BattleBoard, targetFieldId: FieldId?): BattleBoard {
        // TODO each spell may leave  status effect stacks (% of slot power) and later spells can consume them
        require(targetFieldId != null) { "Cannot affect without target" }
        val targetedEnemy = battleBoard.fields.findById(targetFieldId)?.enemy
        require(targetedEnemy != null) { "Cannot affect without targetedEnemy" }
        val damage = calculateWandDamage()

        return battleBoard.mapEnemies { enemy ->
            if (enemy.id == targetedEnemy.id) {
                val damagedEnemy = enemy.copy(health = enemy.health - damage)
                if (damagedEnemy.health <= 0) null else damagedEnemy
            } else {
                enemy
            }
        }
    }

    private fun calculateWandDamage(): Int {
        return slots.sumOf { slot -> if (slot.hasRequiredMagic()) slot.power else 0 }
    }

}
