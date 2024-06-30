package de.moyapro.colors.game.actions.loot

import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.gameState.*

data class PlaceSpellInLootAction(val spell: Spell) : GameAction("Place spell in loot") {
    override val randomSeed: Int = -1

    override fun apply(oldState: NewGameState): Result<NewGameState> {
        check(oldState.currentRun.spells.none { it.id == spell.id }) { "Spell is already in loot" }
        return Result.success(
            oldState.updateCurrentRun(
                spells = oldState.currentRun.spells + spell
            )
        )
    }
}
