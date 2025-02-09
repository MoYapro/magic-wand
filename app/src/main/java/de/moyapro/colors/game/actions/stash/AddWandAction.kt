package de.moyapro.colors.game.actions.stash

import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.model.MageId
import de.moyapro.colors.game.model.Wand
import de.moyapro.colors.game.model.accessor.findMage
import de.moyapro.colors.game.model.accessor.findWandOnMage
import de.moyapro.colors.game.model.gameState.GameState
import de.moyapro.colors.game.model.gameState.RunData
import de.moyapro.colors.util.minus
import de.moyapro.colors.util.replace

data class AddWandAction(
    val wandToAdd: Wand,
    val targetMageId: MageId,
) : GameAction("Add Wand") {

    override val randomSeed = this.hashCode()

    companion object {
        const val MAX_WANDS = 3
    }

    override fun apply(oldState: GameState): Result<GameState> {
        val currentRun = oldState.currentRun
        if (currentRun.activeWands.size >= MAX_WANDS) return Result.failure(IllegalStateException("There are only $MAX_WANDS wands allowed but trying to add $wandToAdd"))

        val targetMage = currentRun.findMage(targetMageId)
        require(targetMage.wandId == null) { "Mage already has a wand" }
        val previousWand = currentRun.activeWands.findWandOnMage(targetMageId)
        val updatedMage = targetMage.copy(wandId = wandToAdd.id)
        val updatedWandtoAdd = wandToAdd.copy(mageId = updatedMage.id)
        val updatedWands = if (currentRun.activeWands.map(Wand::id).contains(updatedWandtoAdd.id)) currentRun.activeWands else currentRun.activeWands + updatedWandtoAdd
        val wandWithoutThePreviousWand: List<Wand> = updatedWands - previousWand
        val updatedCurrentRun = currentRun.copy(
            activeWands = wandWithoutThePreviousWand,
            mages = currentRun.mages.replace(updatedMage)
        )

        check(checkNoDuplicateWands(updatedCurrentRun)) { "There is a mage with multiple wands after adding a wand" }
        return Result.success(oldState.copy(currentRun = updatedCurrentRun))
    }

    private fun checkNoDuplicateWands(runData: RunData): Boolean {
        val allWands = runData.activeWands + runData.wandsInBag
        return allWands.map(Wand::mageId).size == allWands.map(Wand::mageId).distinct().size
    }
}

