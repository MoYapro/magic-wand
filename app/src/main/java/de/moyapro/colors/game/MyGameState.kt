package de.moyapro.colors.game

import de.moyapro.colors.takeTwo.Wand
import de.moyapro.colors.takeTwo.WandId
import de.moyapro.colors.wand.Magic

data class MyGameState(
    val wands: List<Wand>,
    val magicToPlay: List<Magic>,
) {
    fun findWand(wandId: WandId): Wand? {
        return wands.find { it.id == wandId }
    }
}