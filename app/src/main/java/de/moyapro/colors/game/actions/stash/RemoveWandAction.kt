package de.moyapro.colors.game.actions.stash

import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.model.Wand
import de.moyapro.colors.game.model.accessor.findMage
import de.moyapro.colors.game.model.gameState.GameState
import de.moyapro.colors.util.replace

data class RemoveWandAction(val wandToRemove: Wand) : GameAction("Remove wand from mage") {
    override val randomSeed: Int = -1

    override fun apply(oldState: GameState): Result<GameState> {
        val currentRun = oldState.currentRun
        check(currentRun.activeWands.map(Wand::id).contains(wandToRemove.id)) { "Could not find wand to remove" }
        check(currentRun.mages.findMage(wandToRemove.id) != null) { "Could not remove wand because no mage is holding it" }
        val wandsToKeep = currentRun.activeWands.filter { it.id != wandToRemove.id }
        val updatedMage = currentRun.mages.findMage(wandToRemove.id)!!.copy(wandId = null)
        check(currentRun.activeWands.size - wandsToKeep.size == 1) { "Did not remove exactly one wand but ${wandsToKeep.size}" }
        return Result.success(
            oldState.updateCurrentRun(
                activeWands = wandsToKeep,
                mages = currentRun.mages.replace(updatedMage)
            )
        )
    }
}
