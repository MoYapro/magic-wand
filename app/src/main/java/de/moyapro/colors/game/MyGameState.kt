package de.moyapro.colors.game

import de.moyapro.colors.wand.Wand
import java.util.UUID

data class MyGameState(val wands: List<Wand>) {
    fun findWand(wandId: UUID): Wand? {
        return wands.find { it.id == wandId }
    }
}
