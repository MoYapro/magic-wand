package de.moyapro.colors.game

import de.moyapro.colors.game.model.Bonk
import de.moyapro.colors.game.model.Magic
import de.moyapro.colors.game.model.MagicSlot
import de.moyapro.colors.game.model.MagicType
import de.moyapro.colors.game.model.Slot
import de.moyapro.colors.game.model.Splash
import de.moyapro.colors.game.model.Wand
import de.moyapro.colors.wand.getExampleWandWithSingleSlot
import de.moyapro.colors.wand.getExampleWandWithTwoSlots
import io.kotest.matchers.shouldBe
import org.junit.Test

internal class WandTest {

    @Test
    fun createEmpty() {
        Wand().slots shouldBe emptyList()
    }

    @Test
    fun addSpell() {
        val spell = Bonk(
            magicSlots = listOf(MagicSlot(requiredMagic = Magic(type = MagicType.SIMPLE)))
        )
        val (wand, slot) = getExampleWandWithSingleSlot(spell = spell)
        wand.putSpell(slot.id, spell)
            .slots.single().spell?.magicSlots?.single()?.requiredMagic?.type shouldBe MagicType.SIMPLE
    }

    @Test
    fun addTwoSpells() {
        val spell1 = Bonk()
        val spell2 = Splash()
        val (wand, slot1, slot2) = getExampleWandWithTwoSlots()
        wand
            .putSpell(slot1.id, spell1)
            .putSpell(slot2.id, spell2)
            .slots.map { it.spell } shouldBe listOf(spell1, spell2)
    }

    @Test
    fun placeMagicInAvailableSlot() {
        val magic = Magic()
        val spell = Bonk(
            magicSlots = listOf(
                MagicSlot(Magic()),
                MagicSlot(Magic())
            )
        )
        val slot = Slot(
            power = 1,
            level = 0,
            spell = spell
        )
        val (wand, theSlot) = getExampleWandWithSingleSlot(slot, spell)
        spell.magicSlots.size shouldBe 2
        wand.slots.first().spell?.magicSlots?.size shouldBe 2
        val wandWithMagic1 = wand.putMagic(theSlot.id, magic).getOrThrow()
        wandWithMagic1.slots.single().spell?.magicSlots?.first()?.placedMagic shouldBe magic
        wandWithMagic1.slots.single().spell?.magicSlots?.last()?.placedMagic shouldBe null
        val wandWithMagic2 = wandWithMagic1.putMagic(theSlot.id, magic).getOrThrow()
        wandWithMagic2.slots.single().spell?.magicSlots?.first()?.placedMagic shouldBe magic
        wandWithMagic2.slots.single().spell?.magicSlots?.last()?.placedMagic shouldBe magic
    }

    @Test
    fun placeMagicNoAvailableSlot() {
        val (fullWand, slot) = getExampleWandWithSingleSlot()
        fullWand.putMagic(slot.id, fullWand.slots.single().spell!!.magicSlots.single().requiredMagic).isFailure shouldBe true
    }

    @Test
    fun hasAnySpellToZap_yes() {
        getExampleWandWithSingleSlot().first.hasAnySpellToZap() shouldBe true
    }

    @Test
    fun hasAnySpellToZap_no() {
        val wand = getExampleWandWithSingleSlot(spell = Bonk(magicSlots = listOf(MagicSlot(Magic())))).first
        wand.hasAnySpellToZap() shouldBe false
    }

}
