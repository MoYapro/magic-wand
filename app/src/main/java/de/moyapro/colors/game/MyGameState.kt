package de.moyapro.colors.game

import de.moyapro.colors.takeTwo.WandId
import de.moyapro.colors.takeTwo.Wand

data class MyGameState(val wands: List<Wand>) {
    fun findWand(wandId: WandId): Wand? {
        return wands.find { it.id == wandId }
    }
}
