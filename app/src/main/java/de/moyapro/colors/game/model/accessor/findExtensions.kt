package de.moyapro.colors.game.model.accessor

import de.moyapro.colors.game.model.Mage
import de.moyapro.colors.game.model.MageId
import de.moyapro.colors.game.model.Slot
import de.moyapro.colors.game.model.SlotId
import de.moyapro.colors.game.model.Wand
import de.moyapro.colors.game.model.WandId
import de.moyapro.colors.game.model.interfaces.HasId
import de.moyapro.colors.game.model.interfaces.HasMages
import de.moyapro.colors.game.model.interfaces.HasWands
import de.moyapro.colors.util.replace

fun HasWands.findWand(wandId: WandId): Wand {
    val foundWand: Wand? = wands.findWand(wandId)
    check(foundWand != null) { "Could not find wand for id $wandId in $this" }
    return foundWand
}

fun List<Wand>.findWand(wandId: WandId) = this.find { it.id == wandId }

fun List<Slot>.findSlot(slotId: SlotId) = this.find { it.id == slotId }

fun List<Wand>.findWandOnMage(mageId: MageId) = this.find { it.mageId == mageId }

fun HasWands.updateWand(wand: Wand): List<Wand> = wands.replace(wand)

fun HasMages.findMage(mageId: MageId): Mage {
    val foundMage = mages.findMage(mageId)
    check(foundMage != null) { "Could not find mage for id $mageId in $this" }
    return foundMage
}

fun HasMages.findMage(wandId: WandId): Mage {
    val foundMage = mages.findMage(wandId)
    check(foundMage != null) { "Could not find mage for wandId $wandId in $this" }
    return foundMage
}

fun List<Mage>.findMage(mageId: MageId): Mage? {
    return this.find { it.id == mageId }
}

fun List<Mage>.findMage(wandId: WandId): Mage? {
    return this.find { it.wandId == wandId }
}

fun <T, U : HasId<T>> List<U>.findById(id: T): U? {
    return this.firstOrNull { it.id == id }
}

fun List<Wand>.inOrder(): List<Wand> {
    require(this.all { wand -> wand.mageId?.id != null })
    return this.sortedBy { wand -> wand.mageId!!.id }.mapNotNull { wand -> findWand(wand.id) }
}

fun HasMages.updateMage(mage: Mage): List<Mage> = mages.replace(mage)