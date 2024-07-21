package de.moyapro.colors.game.actions.fight

import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.enemy.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.accessor.*
import de.moyapro.colors.game.model.gameState.*
import de.moyapro.colors.util.*

data class ZapAction(
    val wandId: WandId,
    override val target: EnemyId? = null,
) : GameAction("Zap") {

    override val randomSeed = this.hashCode()

    override fun apply(oldState: NewGameState): Result<NewGameState> {
        val wandToZap = oldState.currentFight.wands.findById(wandId)
        check(wandToZap != null) { "Wand to zap does not exist for id $wandId" }
        val damage = calculateWandDamage(wandToZap)
        val zappedWand = wandToZap.copy(slots = removeMagicFromFullSlots(wandToZap))
        val updatedBattleBoard = oldState.currentFight.battleBoard.mapEnemies { enemy ->
            if (enemy.id == target) {
                val damagedEnemy = enemy.copy(health = enemy.health - damage)
                if (damagedEnemy.health <= 0) null else damagedEnemy
            } else {
                enemy
            }
        }

        return Result.success(
            oldState.copy(
                currentFight = oldState.currentFight.copy(
                    battleBoard = updatedBattleBoard,
                    wands = oldState.currentFight.wands.replace(zappedWand)
                )
            )
        )
    }

    override fun onAddAction(actions: MutableList<GameAction>) {
        actions.add(ShowTargetSelectionAction(this))
    }

    override fun requireTargetSelection() = true

    private fun removeMagicFromFullSlots(wand: Wand): List<Slot> = wand.slots.map { slot ->
        if (slot.hasRequiredMagic() && slot.spell != null) {
            val updatedSpell =
                slot.spell.copy(magicSlots = slot.spell.magicSlots.map { magicSlot ->
                    magicSlot.copy(placedMagic = null)
                })
            slot.copy(spell = updatedSpell)
        } else {
            slot
        }
    }

    private fun calculateWandDamage(wand: Wand): Int {
        return wand.slots.sumOf { slot -> if (slot.hasRequiredMagic()) slot.power else 0 }
    }

    override fun isValidTarget(enemy: Enemy): Boolean {
        return true
    }

    override fun withSelection(targetId: EnemyId): GameAction {
        return this.copy(target = targetId)
    }

}
