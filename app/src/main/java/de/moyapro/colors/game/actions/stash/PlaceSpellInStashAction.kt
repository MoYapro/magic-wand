package de.moyapro.colors.game.actions.stash

import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.model.Spell
import de.moyapro.colors.game.model.gameState.GameState

data class PlaceSpellInStashAction(val spell: Spell<*>) : GameAction("Place spell in stash") {
    override val randomSeed: Int = -1

    override fun apply(oldState: GameState): Result<GameState> {
        check(oldState.currentRun.spells.none { it.id == spell.id }) { "Spell is already in stash" }
        return Result.success(
            oldState.updateCurrentRun(
                spells = oldState.currentRun.spells + spell
            )
        )
    }
}
