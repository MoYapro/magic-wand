package de.moyapro.colors.game.model

import de.moyapro.colors.game.enemy.Enemy
import de.moyapro.colors.game.model.accessor.findById
import de.moyapro.colors.game.model.gameState.BattleBoard
import de.moyapro.colors.game.model.interfaces.HasId
import de.moyapro.colors.util.mapIf

data class Wand(
    override val id: WandId = WandId(),
    val slots: List<Slot> = emptyList(),
    val zapped: Boolean = false,
    val mageId: MageId? = null,
) : HasId<WandId> {
    fun putSpell(slotId: SlotId, spell: Spell<*>): Wand {
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

    fun hasAnySpellToZap(): Boolean {
        return slots.any { it.spell != null && it.hasRequiredMagic() }
    }

    fun affect(battleBoard: BattleBoard, targetFieldId: FieldId?): BattleBoard {
        require(targetFieldId != null) { "Cannot affect without target" }
        val targetedEnemy = battleBoard.fields.findById(targetFieldId)?.enemy
        require(targetedEnemy != null) { "Cannot affect without targetedEnemy" }
        val spellsToExecute = slots.filter { it.spell != null && it.hasRequiredMagic() }
        require(spellsToExecute.isNotEmpty(), { "Cannot affect without spells to execute" })

        return battleBoard.mapEnemies { enemy ->
            if (enemy.id == targetedEnemy.id) {
                spellsToExecute.fold(enemy, ::applyNextSpellToEnemy)
            } else {
                enemy
            }
        }.mapEnemies { enemy ->
            if (enemy.health > 0) enemy else null
        }
    }

    private fun applyNextSpellToEnemy(enemy: Enemy?, slot: Slot): Enemy? {
        require(slot.spell != null) { "Must use a slot with a spell to apply to enemy" }
        if (null == enemy) return null
        val damagedEnemy = slot.spell.damage(enemy, slot.power)
        val effectedEnemy = slot.spell.applyEffect(damagedEnemy, slot.power)
        return effectedEnemy
    }

}
