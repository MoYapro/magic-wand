package de.moyapro.colors.game

import de.moyapro.colors.takeTwo.Mage
import de.moyapro.colors.takeTwo.MageId
import de.moyapro.colors.takeTwo.Wand
import de.moyapro.colors.takeTwo.WandId
import de.moyapro.colors.wand.Magic

data class MyGameState(
    val enemies: List<Enemy>,
    val wands: List<Wand>,
    val magicToPlay: List<Magic>,
    val currentTurn: Int,
    val mages: List<Mage> = emptyList(),
) {

    fun findWand(wandId: WandId): Wand? {
        return wands.find { it.id == wandId }
    }

    fun findWand(mageId: MageId): Wand? {
        return wands.find { it.mageId == mageId }
    }

    fun findMage(wandId: WandId): Mage? {
        return mages.find { it.wandId == wandId }
    }

    fun findMage(mageId: MageId): Mage? {
        return mages.find { it.id == mageId }
    }


}
