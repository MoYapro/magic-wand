package de.moyapro.colors.wand

import io.kotest.matchers.shouldBe
import org.junit.Test

internal class WandTest {
    val spellName = "::SpellName::"

    @Test
    fun renderEmpty() {
        Wand().render() shouldBe """
            --------------
            
            --------------
        """.trimIndent()
    }

    @Test
    fun addSpell() {
        val spell = Spell(spellName, 1)
        Wand().withSpell(spell).render() shouldBe """
            --------------
            0 [ - / 1 ] $spellName
            --------------
        """.trimIndent()
    }

    @Test
    fun placeMagicInAvailableSlot() {
        val magic = Magic()
        val wand = Wand().withSpell(Spell(spellName, 2))
        val (leftoverMagic, wandWithMagic) = wand.placeMagic(magic)
        leftoverMagic shouldBe NO_MAGIC
        wandWithMagic.render() shouldBe """
            --------------
            0 [ 1 / 1 ] $spellName
            --------------
        """.trimIndent()
    }

}

