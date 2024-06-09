package de.moyapro.colors.game.actions.loot

import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.util.*

data class AddWandAction(
    val wandToAdd: Wand,
    val targetMageId: MageId,
) : GameAction("Add Wand") {

    override val randomSeed = this.hashCode()

    companion object {
        const val MAX_WANDS = 3
    }

    override fun apply(oldState: MyGameState): Result<MyGameState> {
        if (oldState.wands.size >= MAX_WANDS) return Result.failure(IllegalStateException("There are only $MAX_WANDS allowed"))

        val targetMage = oldState.findMage(targetMageId)
        require(targetMage != null) { "Could not find mage($targetMageId) to add wand to" }
        val previousWand = oldState.findWand(targetMageId)
        val updatedMage = targetMage.copy(wandId = wandToAdd.id)
        val updatedWandtoAdd = wandToAdd.copy(mageId = updatedMage.id)
        val updatedWands = if (oldState.wands.map(Wand::id).contains(updatedWandtoAdd.id)) oldState.wands else oldState.wands + updatedWandtoAdd
        val wandWithoutThePreviousWand: List<Wand> = updatedWands - previousWand
        val finalState = oldState.copy(
            wands = wandWithoutThePreviousWand,
            mages = oldState.mages.replace(updatedMage)
        )

        val noDuplicates = finalState.wands.map(Wand::mageId).size == finalState.wands.map(Wand::mageId).distinct().size
        check(noDuplicates) { "There is a mage with multiple wands after adding a wand" }
        check(finalState.wands.size == finalState.wands.distinct().size) { "There are duplicated after adding a wand" }
        return Result.success(finalState)
    }
}

