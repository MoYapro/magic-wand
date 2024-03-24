package de.moyapro.colors.game

import de.moyapro.colors.takeTwo.Mage
import de.moyapro.colors.takeTwo.MageId
import de.moyapro.colors.takeTwo.Slot
import de.moyapro.colors.takeTwo.SlotId
import de.moyapro.colors.takeTwo.SpellId
import de.moyapro.colors.takeTwo.Wand
import de.moyapro.colors.takeTwo.WandId
import de.moyapro.colors.wand.Magic
import de.moyapro.colors.wand.Spell

data class MyGameState(
    val enemies: List<Enemy>,
    val wands: List<Wand>,
    val magicToPlay: List<Magic>,
    val currentTurn: Int,
    val mages: List<Mage> = emptyList(),
    val spellsInStash: List<Spell> = emptyList(),
) {

    fun findWand(wandId: WandId): Wand? {
        return wands.find { it.id == wandId }
    }

    fun findWand(mageId: MageId): Wand? {
        return wands.find { it.mageId == mageId }
    }

    fun findWand(slotId: SlotId): Wand? {
        return wands.find { wand -> wand.slots.any { slot -> slot.id == slotId } }
    }

    fun findMage(wandId: WandId): Mage? {
        return mages.find { it.wandId == wandId }
    }

    fun findMage(mageId: MageId): Mage? {
        return mages.find { it.id == mageId }
    }

    fun findSlot(slotId: SlotId): Slot? {
        return wands.map(Wand::slots).flatten().firstOrNull() { slot -> slot.id == slotId }
    }

    fun findSlot(spellId: SpellId): Slot? {
        return wands.map(Wand::slots).flatten().firstOrNull() { slot -> slot.spell?.id == spellId }
    }


}
