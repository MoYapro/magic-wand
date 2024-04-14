package de.moyapro.colors.game

import de.moyapro.colors.takeTwo.*
import de.moyapro.colors.wand.*

data class Loot(
    val wands: List<Wand> = emptyList(),
    val spells: List<Spell> = emptyList(),
) {
    fun findWand(wandId: WandId): Wand? {
        return wands.singleOrNull { it.id == wandId }
    }

}
