package de.moyapro.colors.game.actions.loot

import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.wand.*

data class RemoveSpellFromLootAction(
    val spell: Spell,
) : GameAction("Remove spell from loot") {
    override val randomSeed: Int = this.hashCode()

    override fun apply(oldState: MyGameState): Result<MyGameState> {
        require(oldState.loot.spells.contains(spell)) { "Could not remove spell from lot because it was not in there" }
        return Result.success(
            oldState.copy(loot = oldState.loot.copy(spells = oldState.loot.spells.filter { it.id != spell.id }))
        )
    }
}
