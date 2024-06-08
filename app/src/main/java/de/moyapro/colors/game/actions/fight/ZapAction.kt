package de.moyapro.colors.game.actions.fight

import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.enemy.*
import de.moyapro.colors.takeTwo.*
import de.moyapro.colors.util.*

data class ZapAction(
    val wandId: WandId,
    override val target: EnemyId? = null
) : GameAction("Zap"){

    override val randomSeed = this.hashCode()

    override fun apply(oldState: MyGameState): Result<MyGameState> {
        val updatedWand = setWandZapped(oldState).onFailure { return Result.failure(it) }
        val damage = updatedWand.map(::calculateWandDamage)
        val updatedWand2 = removeMagicFromFullSlots(updatedWand.getOrThrow())
        val updatedEnemy =
            oldState.enemies.singleOrNull { it.id == target }
                ?.let { firstEnemy -> firstEnemy.copy(health = firstEnemy.health - damage.getOrThrow()) }
        val updatedEnemies = if (null != updatedEnemy) oldState.enemies.replace(
            updatedEnemy.id,
            updatedEnemy
        ) else oldState.enemies
        val removedKilledEnemies = updatedEnemies.filter { enemy -> enemy.health > 0 }
        return Result.success(
            oldState.copy(
                enemies = removedKilledEnemies,
                wands = oldState.wands.replace(wandId, updatedWand2),
            )
        )
    }

    override fun onAddAction(actions: MutableList<GameAction>) {
        actions.add(ShowTargetSelectionAction(this))
    }

    override fun requireTargetSelection() = true

    private fun removeMagicFromFullSlots(wand: Wand): Wand {
        val updatedSlots = wand.slots.map { slot ->
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
        return wand.copy(slots = updatedSlots)
    }

    private fun setWandZapped(oldState: MyGameState): Result<Wand> {
        val targetWand: Wand = oldState.findWand(wandId)
            ?: return Result.failure(IllegalStateException("Could not find wand with id $wandId"))
        return Result.success(targetWand.copy(zapped = true))
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
