package de.moyapro.colors.game.effect

import de.moyapro.colors.game.enemy.Enemy
import kotlin.math.max

const val BASE_EVAPORATION_RATE = 1

fun applyWet(enemy: Enemy): Enemy {
    val currentWet = enemy.statusEffects[Effect.WET] ?: return enemy
    val currentBurning = enemy.statusEffects[Effect.BURNING] ?: BASE_EVAPORATION_RATE

    val remainingWetStacks = max(currentWet - currentBurning, 0)
    val remainingBurningStacks = max(currentBurning - currentWet, 0)


    val statusEffects = enemy.statusEffects
        .setOrRemove(Effect.WET, remainingWetStacks)
        .setOrRemove(Effect.BURNING, remainingBurningStacks)

    return enemy.copy(statusEffects = statusEffects)
}


private fun Map<Effect, Int>.setOrRemove(effect: Effect, newAmount: Int): Map<Effect, Int> {
    return if (newAmount <= 0) this.minus(effect)
    else this.plus(effect to newAmount)
}