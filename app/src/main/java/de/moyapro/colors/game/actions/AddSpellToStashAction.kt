package de.moyapro.colors.game.actions

import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.wand.Spell

data class AddSpellToStashAction(private val spell: Spell) : GameAction("Add spell to stash") {
    override val randomSeed: Int = this.hashCode()

    override fun apply(oldState: MyGameState): Result<MyGameState> {
        return Result.success(oldState.copy(spellsInStash = oldState.spellsInStash + spell))
    }
}