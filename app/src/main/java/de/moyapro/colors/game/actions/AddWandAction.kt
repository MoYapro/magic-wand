package de.moyapro.colors.game.actions

import de.moyapro.colors.game.*
import de.moyapro.colors.takeTwo.*
import de.moyapro.colors.util.*

data class AddWandAction(
    val wandToAdd: Wand,
    /**
     * action to execute if the wandToAdd replaces another wand
     */
    val targetMageId: MageId,
    val onReplaceAction: (replacedWand: Wand) -> GameAction = { NoOp() },
) : GameAction("Add Wand") {

    override val randomSeed = this.hashCode()

    companion object {
        const val MAX_WANDS = 3
    }

    override fun apply(oldState: MyGameState): Result<MyGameState> {
        if (oldState.wands.size >= MAX_WANDS) return Result.failure(IllegalStateException("There are only $MAX_WANDS allowed"))
        val currentWand = oldState.findWand(targetMageId)
        val stateWithNewWandAdded: MyGameState = giveWandToMage(targetMageId, wandToAdd, oldState)

        val finalState: Result<MyGameState> = if (currentWand == null) {
            Result.success(stateWithNewWandAdded)
        } else {
            val stateWithNewAddedAndCurrentRemoved = removeFromWands(currentWand, stateWithNewWandAdded)
            if (wandToAdd.mageId == null) {
                onReplaceAction(currentWand.removeMageId()).apply(stateWithNewAddedAndCurrentRemoved)
            } else {
                Result.success(giveWandToMage(wandToAdd.mageId, currentWand, stateWithNewAddedAndCurrentRemoved))
            }
        }

        check(finalState.getOrThrow().wands.size == finalState.getOrThrow().wands.distinct().size) { "There are duplicated after adding a wand" }
        return finalState
    }

    private fun removeFromWands(currentWand: Wand, oldState: MyGameState): MyGameState {
        return oldState.copy(wands = oldState.wands.filter { it.id != currentWand.id })
    }

    private fun giveWandToMage(targetMageId: MageId, wandToAdd: Wand, oldState: MyGameState): MyGameState {
        val targetMage = oldState.findMage(targetMageId)
        check(targetMage != null) { "Could not find mage($targetMageId) to add wand to" }
        val currentWand: Wand? = oldState.findWand(targetMageId)
        val (updatedMage, updatedWandtoAdd) = if (currentWand == null) {
            justGiveWandToMage(targetMage, wandToAdd)
        } else {
            giveAndReplaceWandOnMage(targetMage, currentWand, wandToAdd)
        }

        val updatedWands = if (oldState.wands.map(Wand::id).contains(updatedWandtoAdd.id)) oldState.wands else oldState.wands + updatedWandtoAdd
        return oldState.copy(
            wands = updatedWands, mages = oldState.mages.replace(updatedMage)
        )
    }

    private fun giveAndReplaceWandOnMage(foundMage: Mage, currentWand: Wand, wandToAdd: Wand): Pair<Mage, Wand> {
        val wandToAddMageId = currentWand.mageId
        val currentWandUpdated = currentWand.copy(mageId = null)
        val updatedMageAndWand = justGiveWandToMage(foundMage, wandToAdd)
        onReplaceAction(currentWandUpdated)
        return updatedMageAndWand
    }

    private fun justGiveWandToMage(
        foundMage: Mage,
        wandToAdd: Wand,
    ): Pair<Mage, Wand> {
        val updatedMage = foundMage.copy(wandId = wandToAdd.id)
        val updatedWandtoAdd = wandToAdd.copy(mageId = updatedMage.id)
        return Pair(updatedMage, updatedWandtoAdd)
    }

}
