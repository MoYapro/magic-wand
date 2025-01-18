package de.moyapro.colors.game.model

import android.util.Log
import de.moyapro.colors.util.HashUuidFunctions
import kotlin.random.Random

data class MagicGenerator(val magicType: MagicType, val amountRange: IntRange, val randomSeed: Int = -1) {
    init {
        Log.d("magigGenerator", "init")
    }

    private lateinit var random: Random

    fun generate(): List<Magic> {
        random = Random(randomSeed)

        val magicCount = amountRange.random(random)
        return (1..magicCount).map {
            Magic(id = MagicId(HashUuidFunctions.v5(random.nextDouble().toString())), type = magicType)
        }
    }
}
