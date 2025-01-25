package de.moyapro.colors.ui.view.components

import de.moyapro.colors.game.model.MagicGenerator
import de.moyapro.colors.game.model.MagicType
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.Test

class GeneratorsRowTest {

    @Test
    fun groupGenerators() {
        val generators = listOf(
            MagicGenerator(MagicType.RED, 3..5), MagicGenerator(MagicType.RED, 13..19), MagicGenerator(MagicType.GREEN, 0..1)
        )
        val aggregatedGenerators = getAggegatedGenerators(generators)
        aggregatedGenerators[MagicType.RED] shouldBe 16..24
        aggregatedGenerators[MagicType.GREEN] shouldBe 0..1
        aggregatedGenerators.keys shouldHaveSize 2
    }
}