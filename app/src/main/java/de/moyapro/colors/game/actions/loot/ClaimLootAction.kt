package de.moyapro.colors.game.actions.loot

import android.util.Log
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.model.Spell
import de.moyapro.colors.game.model.Wand
import de.moyapro.colors.game.model.gameState.GameState
import de.moyapro.colors.util.hasDuplicates


const val TAG = "LootActivity"

data class ClaimLootAction(val newSpells: List<Spell<*>>, val newWands: List<Wand>) : GameAction("Claim Loot") {
    override val randomSeed: Int = 1

    override fun apply(oldState: GameState): Result<GameState> {
        Log.d(TAG, "claim loot: $newSpells, $newWands")
        val updatedSpells = oldState.currentRun.spells + newSpells
        val updatedWands = oldState.currentRun.wandsInBag + newWands
        if (newSpells.isEmpty() && newWands.isEmpty()) return Result.success(oldState)
        if (updatedSpells.hasDuplicates()) return Result.failure(IllegalArgumentException("ClaimLootAction: Duplicate spells"))
        if (updatedWands.hasDuplicates()) return Result.failure(IllegalArgumentException("ClaimLootAction: Duplicate wands"))
        return Result.success(
            oldState.copy(
                currentRun = oldState.currentRun.copy(
                    spells = updatedSpells, wandsInBag = updatedWands
                )
            )
        )
    }

}
