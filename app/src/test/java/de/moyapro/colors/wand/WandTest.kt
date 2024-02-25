package de.moyapro.colors.wand

import de.moyapro.colors.takeTwo.Slot
import de.moyapro.colors.takeTwo.Wand
import io.kotest.matchers.shouldBe
import org.junit.Test

internal class WandTest {
    val spellName = "::SpellName::"

    @Test
    fun createEmpty() {
        Wand().slots shouldBe emptyList()
    }

    @Test
    fun addSpell() {
        val spell = Spell(spellName)
        val (wand, slot) = getExampleWandWithSingleSlot()
        wand.putSpell(slot.id, spell).slots.single().magicSlots.single() shouldBe MagicSlot(Magic())
    }

    @Test
    fun addTwoSpells() {
        val spell1 = Spell(spellName + "1")
        val spell2 = Spell(spellName + "2")
        val (wand, slot1, slot2) = getExampleWandWithTwoSlots()
        wand
            .putSpell(slot1.id, spell1)
            .putSpell(slot2.id, spell2)
            .slots.map { it.spell } shouldBe listOf(spell1, spell2)
    }

    @Test
    fun placeMagicInAvailableSlot() {
        val magic = Magic()
        val (wand, slot) = getExampleWandWithSingleSlot(
            Slot(
                power = 1,
                level = 0,
                magicSlots = listOf(
                    MagicSlot(Magic()),
                    MagicSlot(Magic())
                )
            )
        )
        val wandWithMagic1 = wand.putMagic(slot.id, magic).getOrThrow()
        wandWithMagic1.slots.single().magicSlots.first().placedMagic shouldBe magic
        wandWithMagic1.slots.single().magicSlots.last().placedMagic shouldBe null
        val wandWithMagic2 = wandWithMagic1.putMagic(slot.id, magic).getOrThrow()
        wandWithMagic2.slots.single().magicSlots.first().placedMagic shouldBe magic
        wandWithMagic2.slots.single().magicSlots.last().placedMagic shouldBe magic
    }


    @Test
    fun placeMagicNoAvailableSlot() {
        val magic = Magic()
        val (wand, slot) = getExampleWandWithSingleSlot()
        val fullWand = wand.putMagic(slot.id, magic).getOrThrow()
        fullWand.putMagic(slot.id, magic).isFailure shouldBe true
    }

}
