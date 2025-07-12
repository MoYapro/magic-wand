package de.moyapro.colors.game.effect

import de.moyapro.colors.game.enemy.Enemy


fun applyFire(enemy: Enemy): Enemy {
    val currentFire = enemy.statusEffects[Effect.BURNING] ?: return enemy
    val grownFireStacks = currentFire + 1
    val health = enemy.health - currentFire
    val statusEffects = enemy.statusEffects.plus(Effect.BURNING to grownFireStacks)
    return enemy.copy(health = health, statusEffects = statusEffects)
}