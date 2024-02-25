package de.moyapro.colors.wand

import de.moyapro.colors.takeTwo.Slot
import de.moyapro.colors.takeTwo.Wand

fun getExampleWandWithSingleSlot(slot: Slot? = null): Pair<Wand, Slot> {
    val actualSlot = slot ?: Slot(level = 0, magicSlots = listOf(MagicSlot(Magic())), power = 1)
    val newWand: Wand =
        Wand(slots = listOf(actualSlot)).putSpell(slotId = actualSlot.id, Spell("spell"))
    return Pair(newWand, actualSlot)
}

fun getExampleWandWithTwoSlots(): Triple<Wand, Slot, Slot> {
    val slot1 = Slot(level = 0, magicSlots = listOf(MagicSlot(Magic())), power = 1)
    val slot2 = Slot(level = 0, magicSlots = listOf(MagicSlot(Magic(MagicType.GREEN))), power = 2)
    val newWand: Wand =
        Wand(slots = listOf(slot1, slot2))
            .putSpell(slotId = slot1.id, Spell("spell"))
            .putSpell(slotId = slot2.id, Spell("spell"))
    return Triple(newWand, slot1, slot2)
}
