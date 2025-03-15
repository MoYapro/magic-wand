package de.moyapro.colors.game.actions.loot

import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.model.Spell
import de.moyapro.colors.game.model.Wand
import de.moyapro.colors.game.model.gameState.GameState

data class ClaimLootAction(val newSpells: List<Spell<*>>, val newWands: List<Wand>) : GameAction("Claim Loot") {
    override val randomSeed: Int = 1

    override fun apply(oldState: GameState): Result<GameState> {
        if (newSpells.isEmpty() && newWands.isEmpty()) return Result.success(oldState)
        return Result.success(
            oldState.copy(
                currentRun = oldState.currentRun.copy(
                    spells = oldState.currentRun.spells + newSpells,
                    wandsInBag = oldState.currentRun.wandsInBag + newWands
                )
            )
        )
    }

}
