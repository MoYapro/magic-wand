package de.moyapro.colors.game.enemy

import de.moyapro.colors.game.effect.*

data class StatusEffect(
    val effect: Effect,
    val amount: Int,
) {

    operator fun plus(additionalEffect: StatusEffect): StatusEffect {
        require(this.effect == additionalEffect.effect) { "Can only add effects of the same type" }
        return this.copy(amount = this.amount + additionalEffect.amount)
    }
}

operator fun Collection<StatusEffect>.plus(additionalEffects: Collection<StatusEffect>): Collection<StatusEffect> {
    val currentMap = this.associate { (effect, amount) -> Pair(effect, amount) }.toMutableMap()
    additionalEffects.forEach { (additionalEffect, additionalAmount) ->
        currentMap[additionalEffect] = (currentMap[additionalEffect] ?: 0) + additionalAmount
    }
    return currentMap.map { (effect, amount) -> StatusEffect(effect, amount) }
}
