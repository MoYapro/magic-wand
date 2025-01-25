package de.moyapro.colors.game.model

import android.util.Log
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.BeforeClass
import org.junit.Test

class MagicGeneratorTest {

    companion object {
        @BeforeClass
        @JvmStatic
        fun setup() {
            mockkStatic(Log::class)
            every { Log.d(any(), any()) } returns 1
            every { Log.e(any(), any()) } returns 1
            every { Log.i(any(), any()) } returns 1
        }
    }

    @Test
    fun `generator creates magic`() {
        MagicGenerator(MagicType.SIMPLE, 1..1).generate(1).map(Magic::type) shouldBe listOf(MagicType.SIMPLE)
    }

    @Test
    fun `generator creates correct magic`() {
        MagicGenerator(MagicType.GREEN, 1..1).generate(1).map(Magic::type) shouldBe listOf(MagicType.GREEN)
    }

    @Test
    fun `generator creates new magic every time`() {
        val magicGenerator = MagicGenerator(MagicType.SIMPLE, 1..1)
        val currentTurn = 1
        val generatedMagicOne = magicGenerator.generate(currentTurn)
        val generatedMagicTwo = magicGenerator.generate(currentTurn + 1)
        generatedMagicOne.single().id shouldNotBe generatedMagicTwo.single().id
    }


    @Test
    fun `generator creates lots of magic`() {
        val generatedMagic = MagicGenerator(MagicType.SIMPLE, 1..100, 99).generate(99).map(Magic::type)
        generatedMagic.all { it == MagicType.SIMPLE } shouldBe true
        generatedMagic shouldHaveSize 84
    }
}