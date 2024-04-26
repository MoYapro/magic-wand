package de.moyapro.colors.game

import de.moyapro.colors.takeTwo.*
import de.moyapro.colors.util.*
import de.moyapro.colors.util.FightOutcome.ONGOING
import de.moyapro.colors.wand.*

data class MyGameState(
    val enemies: List<Enemy>,
    val wands: List<Wand>,
    val magicToPlay: List<Magic>,
    val currentTurn: Int,
    val mages: List<Mage> = emptyList(),
    val fightHasEnded: FightOutcome = ONGOING,
    val loot: Loot = Loot(),
    val actionCounter: Long = 0,
) {

    fun wandsInOrder(): List<Wand> {
        return mages.sortedBy { mage -> mage.id.id }.mapNotNull { mage -> findWand(mage.id) }
    }

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
