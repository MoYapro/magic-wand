package de.moyapro.colors.game.enemy

import de.moyapro.colors.game.effect.Effect

operator fun Map<Effect, Int>.plus(additionalEffects: Map<Effect, Int>): Map<Effect, Int> {
    val currentMap = this.toMutableMap()
    additionalEffects.forEach { (additionalEffect, additionalAmount) ->
        currentMap[additionalEffect] = (currentMap[additionalEffect] ?: 0) + additionalAmount
    }
    return currentMap.toMap()
}
