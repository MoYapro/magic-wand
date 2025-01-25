package de.moyapro.colors.game.model

import de.moyapro.colors.util.HashUuidFunctions
import kotlin.random.Random

data class MagicGenerator(val magicType: MagicType, val amountRange: IntRange, val randomSeed: Int = -1) {

    fun generate(randomSeedAddition: Int): List<Magic> {
        val random: Random = Random(randomSeed + randomSeedAddition)
        val magicCount = amountRange.random(random)
        return (1..magicCount).map {
            Magic(id = MagicId(HashUuidFunctions.v5(random.nextDouble().toString())), type = magicType)
        }
    }
}
