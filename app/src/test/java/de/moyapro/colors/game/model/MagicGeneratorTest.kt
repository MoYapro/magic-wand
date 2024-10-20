package de.moyapro.colors.game.model

import io.kotest.matchers.*
import io.kotest.matchers.collections.*
import org.junit.*
import kotlin.random.*

class MagicGeneratorTest {

    @Test
    fun `generator creates magic`() {
        MagicGenerator(MagicType.SIMPLE, 1..1).generate().map(Magic::type) shouldBe listOf(MagicType.SIMPLE)
    }

    @Test
    fun `generator creates correct magic`() {
        MagicGenerator(MagicType.GREEN, 1..1).generate().map(Magic::type) shouldBe listOf(MagicType.GREEN)
    }

    @Test
    fun `generator creates lots of magic`() {
        val generatedMagic = MagicGenerator(MagicType.SIMPLE, 1..100, Random(99)).generate().map(Magic::type)
        generatedMagic.all { it == MagicType.SIMPLE } shouldBe true
        generatedMagic shouldHaveSize 98
    }
}