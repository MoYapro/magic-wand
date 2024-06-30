package de.moyapro.colors.game.model.accessor

import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.interfaces.*
import de.moyapro.colors.util.*

fun HasWands.findWand(wandId: WandId): Wand {
    val foundWand: Wand? = wands.findWand(wandId)
    check(foundWand != null) { "Could not find wand for id $wandId in $this" }
    return foundWand
}

fun List<Wand>.findWand(wandId: WandId) = this.find { it.id == wandId }

fun List<Wand>.findWandOnMage(mageId: MageId) = this.find { it.mageId == mageId }

fun HasWands.updateWand(wand: Wand): List<Wand> = wands.replace(wand)

fun HasMages.findMage(mageId: MageId): Mage {
    val foundMage = mages.findMage(mageId)
    check(foundMage != null) { "Could not find mage for id $mageId in $this" }
    return foundMage
}

fun List<Mage>.findMage(mageId: MageId): Mage? {
    return this.find { it.id == mageId }
}
fun List<Mage>.findMage(wandId: WandId): Mage? {
    return this.find { it.wandId == wandId }
}

fun HasMages.updateMage(mage: Mage): List<Mage> = mages.replace(mage)