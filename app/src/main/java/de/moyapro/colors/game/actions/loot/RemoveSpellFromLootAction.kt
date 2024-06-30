package de.moyapro.colors.game.actions.loot

import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.gameState.*

data class RemoveSpellFromLootAction(
    val spell: Spell,
) : GameAction("Remove spell from loot") {
    override val randomSeed: Int = this.hashCode()

    override fun apply(oldState: NewGameState): Result<NewGameState> {
        require(oldState.currentRun.spells.contains(spell)) { "Could not remove spell from lot because it was not in there" }
        return Result.success(
            oldState.updateCurrentRun(spells = oldState.currentRun.spells.filter { it.id != spell.id })
        )
    }
}
