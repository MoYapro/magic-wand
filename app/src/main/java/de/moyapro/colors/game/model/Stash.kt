package de.moyapro.colors.game.model

data class Stash(
    val wands: List<Wand> = emptyList(),
    val spells: List<Spell<*>> = emptyList(),
) {
    fun findWand(wandId: WandId): Wand? {
        return wands.singleOrNull { it.id == wandId }
    }

}
