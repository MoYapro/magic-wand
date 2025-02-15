package de.moyapro.colors.game.actions.stash

import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.model.Spell
import de.moyapro.colors.game.model.gameState.GameState

data class RemoveSpellFromStashAction(
    val spell: Spell<*>,
) : GameAction("Remove spell from stash") {
    override val randomSeed: Int = this.hashCode()

    override fun apply(oldState: GameState): Result<GameState> {
        require(oldState.currentRun.spells.contains(spell)) { "Could not remove spell from lot because it was not in there" }
        return Result.success(
            oldState.updateCurrentRun(spells = oldState.currentRun.spells.filter { it.id != spell.id })
        )
    }
}
