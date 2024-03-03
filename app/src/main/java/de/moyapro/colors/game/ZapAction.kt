package de.moyapro.colors.game

import de.moyapro.colors.takeTwo.Wand
import de.moyapro.colors.takeTwo.WandId
import de.moyapro.colors.util.replace

data class ZapAction(
    val wandId: WandId,
) : GameAction {

    override fun apply(oldState: MyGameState): Result<MyGameState> {
        val targetWandWithMagic = updateWands(oldState).onFailure { return Result.failure(it) }
        val damage = 1
        val updatedEnemy =
            oldState.enemies.firstOrNull()
                ?.let { firstEnemy -> firstEnemy.copy(health = firstEnemy.health - damage) }
        return Result.success(
            MyGameState(
                enemies = if (null != updatedEnemy) oldState.enemies.replace(
                    updatedEnemy.id,
                    updatedEnemy
                ) else oldState.enemies,
                wands = oldState.wands.replace(wandId, targetWandWithMagic.getOrThrow()),
                magicToPlay = oldState.magicToPlay
            )
        )
    }

    private fun updateWands(oldState: MyGameState): Result<Wand> {
        val targetWand: Wand = oldState.findWand(wandId)
            ?: return Result.failure(IllegalStateException("Could not find wand with id $wandId"))
        return Result.success(targetWand.copy(zapped = true))
    }

}
