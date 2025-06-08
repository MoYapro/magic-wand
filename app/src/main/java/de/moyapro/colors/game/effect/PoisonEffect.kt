package de.moyapro.colors.game.effect

import de.moyapro.colors.game.enemy.Enemy


fun applyPoison(enemy: Enemy): Enemy {
    val currentPoison = enemy.statusEffects[Effect.POISONED] ?: return enemy
    val remainingPoisonStacks = currentPoison - 1
    val health = enemy.health - currentPoison
    val statusEffects = if (remainingPoisonStacks <= 0) enemy.statusEffects.minus(Effect.POISONED)
    else enemy.statusEffects.plus(Effect.POISONED to remainingPoisonStacks)
    return enemy.copy(health = health, statusEffects = statusEffects)
}