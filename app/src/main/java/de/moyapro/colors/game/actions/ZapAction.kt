package de.moyapro.colors.game.actions

import de.moyapro.colors.game.Enemy
import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.takeTwo.EnemyId
import de.moyapro.colors.takeTwo.Wand
import de.moyapro.colors.takeTwo.WandId
import de.moyapro.colors.util.replace

data class ZapAction(
    val wandId: WandId,
    override val target: EnemyId? = null
) : GameAction("Zap"), RequiresTargetAction {

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

    override fun requireTargetSelection() = true

    private fun removeMagicFromFullSlots(wand: Wand): Wand {
        val updatedSlots = wand.slots.map { slot ->
            if (slot.hasRequiredMagic()) slot.copy(magicSlots = slot.magicSlots.map { magicSlot ->
                magicSlot.copy(placedMagic = null)
            }) else slot
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

    override fun withSelection(target: Enemy): GameAction {
        return this.copy(target = target.id)
    }

}