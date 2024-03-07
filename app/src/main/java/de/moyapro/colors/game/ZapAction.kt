package de.moyapro.colors.game

import de.moyapro.colors.takeTwo.Wand
import de.moyapro.colors.takeTwo.WandId
import de.moyapro.colors.util.replace

data class ZapAction(
    val wandId: WandId,
) : GameAction {

    override fun apply(oldState: MyGameState): Result<MyGameState> {
        val updatedWand = setWandZapped(oldState).onFailure { return Result.failure(it) }
        val damage = updatedWand.map(::calculateWandDamage)
        val updatedWand2 = removeMagicFromFullSlots(updatedWand.getOrThrow())
        val updatedEnemy =
            oldState.enemies.firstOrNull()
                ?.let { firstEnemy -> firstEnemy.copy(health = firstEnemy.health - damage.getOrThrow()) }
        return Result.success(
            oldState.copy(
                enemies = if (null != updatedEnemy) oldState.enemies.replace(
                    updatedEnemy.id,
                    updatedEnemy
                ) else oldState.enemies,
                wands = oldState.wands.replace(wandId, updatedWand2),
            )
        )
    }

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

}
