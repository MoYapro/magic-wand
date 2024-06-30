package de.moyapro.colors.game.actions.loot

import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.accessor.*
import de.moyapro.colors.game.model.gameState.*
import de.moyapro.colors.util.*

data class RemoveWandAction(val wandToRemove: Wand) : GameAction("Remove wand from mage") {
    override val randomSeed: Int = -1

    override fun apply(oldState: NewGameState): Result<NewGameState> {
        val currentRun = oldState.currentRun
        check(currentRun.activeWands.map(Wand::id).contains(wandToRemove.id)) { "Could not find wand to remove" }
        check(currentRun.mages.findMage(wandToRemove.id) != null) { "Could not remove wand because no mage is holding it" }
        val updatedWands = currentRun.activeWands.filter { it.id != wandToRemove.id }
        val updatedMage = currentRun.mages.findMage(wandToRemove.id)!!.copy(wandId = null)
        check(oldState.currentRun.mages.size - updatedWands.size == 1) { "Did not remove exactly one wand" }
        return Result.success(
            oldState.updateCurrentRun(
                activeWands = updatedWands,
                mages = currentRun.mages.replace(updatedMage)
            )
        )
    }
}
