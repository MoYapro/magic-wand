package de.moyapro.colors.game.model.accessor

import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.interfaces.*
import de.moyapro.colors.util.*

fun HasWands.findWand(wandId: WandId): Wand {
    val foundWand: Wand? = wands.find { it.id == wandId }
    check(foundWand != null) { "Could not find wand for id $wandId in $this" }
    return foundWand
}

fun HasWands.updateWand(wand: Wand): List<Wand> = wands.replace(wand)

fun HasMages.findMage(mageId: MageId): Mage {
    val foundMage = mages.find { it.id == mageId }
    check(foundMage != null) { "Could not find mage for id $mageId in $this" }
    return foundMage
}

fun HasMages.updateMage(mage: Mage): List<Mage> = mages.replace(mage)