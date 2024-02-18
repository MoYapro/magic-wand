package de.moyapro.colors.wand

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.Test

internal class WandTest {
    val spellName = "::SpellName::"

    @Test
    fun createEmpty() {
        Wand().magicSlots shouldBe emptyList()
    }

    @Test
    fun addSpell() {
        val spell = Spell(spellName, Magic())
        Wand().withSpell(spell).magicSlots shouldBe listOf(MagicSlot(Magic()))
    }

    @Test
    fun addTwoSpells() {
        val spell1 = Spell(spellName + "1", listOf(Magic(), Magic(), Magic(), Magic()))
        val spell2 = Spell(spellName + "2", listOf(Magic(), Magic()))
        Wand()
            .withSpell(spell1)
            .withSpell(spell2)
            .magicSlots shouldHaveSize 6
    }

    @Test
    fun placeMagicInAvailableSlot() {
        val magic = Magic()
        val wand = Wand().withSpell(Spell(spellName, listOf(Magic(), Magic())))
        val (leftoverMagic1, wandWithMagic1) = wand.placeMagic(magic)
        leftoverMagic1 shouldBe null
        wandWithMagic1.magicSlots shouldBe listOf(MagicSlot(Magic(), full = true), MagicSlot(Magic()))
        val (leftoverMagic2, wandWithMagic2) = wand.placeMagic(magic)
        leftoverMagic2 shouldBe null
        wandWithMagic2.magicSlots shouldBe listOf(MagicSlot(Magic(), full = true), MagicSlot(Magic(), full = true))
        val (leftoverMagic3, wandWithMagic3) = wand.placeMagic(magic)
        leftoverMagic3 shouldBe Magic()
        wandWithMagic3.magicSlots shouldBe listOf(MagicSlot(Magic(), full = true), MagicSlot(Magic(), full = true))
    }


    @Test
    fun placeMagicNoAvailableSlot() {
        val magic = Magic()
        val wand = Wand().withSpell(Spell(spellName, emptyList()))
        val (leftoverMagic, wandWithMagic) = wand.placeMagic(magic)
        leftoverMagic shouldBe magic
        wandWithMagic
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


    @Test
    fun createMagicSlotsFromEmpty() {
        createMagicSlots(emptyList(), emptyList()) shouldBe emptyList()
    }

    @Test
    fun createMagicSlotsFromSpells() {
        val spell = Spell(spellName, Magic())
        createMagicSlots(
            listOf(spell),
            emptyList()
        ) shouldBe listOf(MagicSlot(spell.requiredMagic[0], false))
    }

    @Test
    fun createMagicSlotsFromSpellsAndExistingEmptySlots() {
        val spell = Spell(spellName, Magic())
        createMagicSlots(
            listOf(spell),
            listOf(MagicSlot(spell.requiredMagic[0], false))
        ) shouldBe listOf(MagicSlot(spell.requiredMagic[0], false))
    }

    @Test
    fun createMagicSlotsFromSpellsAndExistingFullSlots() {
        val spell = Spell(spellName, Magic())
        createMagicSlots(
            listOf(spell),
            listOf(MagicSlot(spell.requiredMagic[0], true))
        ) shouldBe listOf(MagicSlot(spell.requiredMagic[0], true))
    }

    @Test
    fun createMagicSlotsFromSpellsAndExistingFullSlotsAndNewMagic() {
        val spell1 = Spell(spellName, Magic())
        val spell2 = Spell(spellName, Magic())
        createMagicSlots(
            listOf(spell1, spell2),
            listOf(MagicSlot(spell1.requiredMagic[0], true)),
            Magic()
        ) shouldBe listOf(
            MagicSlot(spell1.requiredMagic[0], true),
            MagicSlot(spell2.requiredMagic[0], true)
        )
    }

    @Test
    fun createMagicSlotsMixed() {
        val previouslyFilled = Spell(spellName, Magic())
        val filledWithNewMagic = Spell(spellName, Magic())
        val notFilled = Spell(spellName, Magic())
        createMagicSlots(
            listOf(previouslyFilled, filledWithNewMagic, notFilled),
            listOf(MagicSlot(previouslyFilled.requiredMagic[0], true)),
            Magic()
        ) shouldBe listOf(
            MagicSlot(previouslyFilled.requiredMagic[0], true),
            MagicSlot(filledWithNewMagic.requiredMagic[0], true),
            MagicSlot(notFilled.requiredMagic[0], false),
        )
    }

}
