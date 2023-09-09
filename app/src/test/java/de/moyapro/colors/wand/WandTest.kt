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
    fun addTwoSpells() {
        val spell1 = Spell(spellName +"1", 13)
        val spell2 = Spell(spellName +"2", 11)
        Wand()
            .withSpell(spell1)
            .withSpell(spell2)
            .render() shouldBe """
            --------------
            0 [ - / 13 ] ${spellName}1
            0 [ - / 11 ] ${spellName}2
            --------------
        """.trimIndent()
    }

    @Test
    fun placeMagicInAvailableSlot() {
        val magic = Magic()
        val wand = Wand().withSpell(Spell(spellName, 2))
        val (leftoverMagic, wandWithMagic) = wand.placeMagic(magic)
        leftoverMagic shouldBe null
        wandWithMagic.render() shouldBe """
            --------------
            0 [ 1 / 2 ] $spellName
            --------------
        """.trimIndent()
    }

    @Test
    fun place_No_Magic() {
        val magic = Magic(MagicType.NONE)
        val wand = Wand().withSpell(Spell(spellName, 2))
        val (leftoverMagic, wandWithMagic) = wand.placeMagic(magic)
        leftoverMagic shouldBe null
        wandWithMagic.render() shouldBe """
            --------------
            0 [ 0 / 2 ] $spellName
            --------------
        """.trimIndent()
    }

    @Test
    fun placeMagicNoAvailableSlot() {
        val magic = Magic()
        val wand = Wand().withSpell(Spell(spellName, 0))
        val (leftoverMagic, wandWithMagic) = wand.placeMagic(magic)
        leftoverMagic shouldBe magic
        wandWithMagic.render() shouldBe """
            --------------
            0 [ - / 0 ] $spellName
            --------------
        """.trimIndent()
    }

    @Test
    fun activateWand() {
        val (_, wand) = Wand()
            .withSpell(Spell(spellName, 2))
            .placeMagic(Magic())
        wand.canActivate() shouldBe false
        val (_, wandWithMoreMagic) = wand.placeMagic(Magic())
        wandWithMoreMagic.canActivate() shouldBe true
    }

}

