package de.moyapro.colors.game.actions.loot

import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.model.Spell
import de.moyapro.colors.game.model.gameState.GameState

data class PlaceSpellInLootAction(val spell: Spell<*>) : GameAction("Place spell in loot") {
    override val randomSeed: Int = -1

    override fun apply(oldState: GameState): Result<GameState> {
        check(oldState.currentRun.spells.none { it.id == spell.id }) { "Spell is already in loot" }
        return Result.success(
            oldState.updateCurrentRun(
                spells = oldState.currentRun.spells + spell
            )
        )
    }
}
