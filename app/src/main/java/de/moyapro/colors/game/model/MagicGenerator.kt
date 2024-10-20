package de.moyapro.colors.game.model

import kotlin.random.*

data class MagicGenerator(val magicType: MagicType, val amount: IntRange, val random: Random = Random(-1)) {
    fun generate(): List<Magic> {
        val magicCount = amount.random(random)
        return (1..magicCount).map {
            Magic(type = magicType)
        }
    }
}
