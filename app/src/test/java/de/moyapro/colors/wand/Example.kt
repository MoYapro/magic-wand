package de.moyapro.colors.wand

import de.moyapro.colors.takeTwo.Slot
import de.moyapro.colors.takeTwo.Wand

fun getExampleWandWithSingleSlot(slot: Slot? = null, spell: Spell? = null): Pair<Wand, Slot> {
    val actualSlot = slot ?: Slot(level = 0, power = 1)
    val actualSpell = spell ?: Spell(name = "spell", magicSlots = listOf(MagicSlot(Magic())))
    val newWand: Wand =
        Wand(slots = listOf(actualSlot)).putSpell(slotId = actualSlot.id, actualSpell)
    return Pair(newWand, actualSlot)
}

fun getExampleWandWithTwoSlots(): Triple<Wand, Slot, Slot> {
    val slot1 = Slot(level = 0, power = 1)
    val slot2 =
        Slot(level = 0, power = 2)
    val newWand: Wand =
        Wand(slots = listOf(slot1, slot2))
            .putSpell(
                slotId = slot1.id,
                Spell(name = "spell", magicSlots = listOf(MagicSlot(Magic())))
            )
            .putSpell(
                slotId = slot2.id,
                Spell(name = "spell", magicSlots = listOf(MagicSlot(Magic(type = MagicType.GREEN))))
            )
    return Triple(newWand, slot1, slot2)
}
