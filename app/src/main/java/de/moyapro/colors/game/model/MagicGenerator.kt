package de.moyapro.colors.game.model

import de.moyapro.colors.util.HashUuidFunctions
import kotlin.random.Random

data class MagicGenerator(val magicType: MagicType, val amount: IntRange, val randomSeed: Int = -1) {

    private lateinit var random: Random

    fun generate(): List<Magic> {
        random = Random(randomSeed)

        val magicCount = amount.random(random)
        return (1..magicCount).map {
            Magic(id = MagicId(HashUuidFunctions.v5(random.nextDouble().toString())), type = magicType)
        }
    }
}
