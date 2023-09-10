package de.moyapro.colors.wand

import io.kotest.assertions.throwables.shouldThrow
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
        val spell = Spell(spellName, Magic())
        Wand().withSpell(spell).render() shouldBe """
            --------------
            0 [ - / 1 ] $spellName
            --------------
        """.trimIndent()
    }

    @Test
    fun addTwoSpells() {
        val spell1 = Spell(spellName + "1", listOf(Magic(), Magic(), Magic(), Magic()))
        val spell2 = Spell(spellName + "2", listOf(Magic(), Magic()))
        Wand()
            .withSpell(spell1)
            .withSpell(spell2)
            .render() shouldBe """
            --------------
            0 [ - / 4 ] ${spellName}1
            0 [ - / 2 ] ${spellName}2
            --------------
        """.trimIndent()
    }

    @Test
    fun placeMagicInAvailableSlot() {
        val magic = Magic()
        val wand = Wand().withSpell(Spell(spellName, listOf(Magic(), Magic())))
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
        val wand = Wand().withSpell(Spell(spellName, listOf(Magic(), Magic())))
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
        val wand = Wand().withSpell(Spell(spellName, Magic()))
        val (leftoverMagic, wandWithMagic) = wand.placeMagic(magic)
        leftoverMagic shouldBe magic
        wandWithMagic.render() shouldBe """
            --------------
            0 [ - / 0 ] $spellName
            --------------
        """.trimIndent()
    }

    @Test
    fun canActivate() {
        val (_, wand) = Wand()
            .withSpell(Spell(spellName, listOf(Magic(), Magic())))
            .placeMagic(Magic())
        wand.canActivate() shouldBe false
        val (_, wandWithMoreMagic) = wand.placeMagic(Magic())
        wandWithMoreMagic.canActivate() shouldBe true
    }

    @Test
    fun doActivateIfNotReady() {
        val (_, wand) = Wand()
            .withSpell(Spell(spellName, listOf(Magic(), Magic())))
            .placeMagic(Magic())
        shouldThrow<IllegalStateException> { wand.doActivate() }
    }

    @Test
    fun doActivate() {
        val spell = Spell(spellName, listOf(Magic(), Magic()))
        val (_, wand) = Wand()
            .withSpell(spell)
            .placeMagic(Magic())
            .wand
            .placeMagic(Magic())
        val castSpells: List<Spell> = wand.doActivate()
        castSpells shouldBe listOf(spell)
    }

}
