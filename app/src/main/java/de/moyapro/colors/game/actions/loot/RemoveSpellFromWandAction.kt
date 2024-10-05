package de.moyapro.colors.game.actions.loot

import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.accessor.*
import de.moyapro.colors.game.model.gameState.*
import de.moyapro.colors.util.*

data class RemoveSpellFromWandAction(
    val wandId: WandId,
    val slotId: SlotId,
) :
    GameAction("Remove spell from wand") {
    override val randomSeed: Int = this.hashCode()

    override fun apply(oldState: GameState): Result<GameState> {
        val wandInHand = oldState.currentRun.activeWands.findWand(wandId)
        return if (wandInHand != null) removeSpellFromWandInHand(wandInHand, oldState)
        else removeSpellFromWandInLoot(wandId, oldState)
    }

    private fun removeSpellFromWandInHand(wandInHand: Wand, oldState: GameState): Result<GameState> {
        val slotToChange = wandInHand.slots.singleOrNull { it.id == slotId }
        check(slotToChange != null) { "Could not find slot to remove spell from" }
        val updatedSlot = slotToChange.copy(spell = null)
        val updatedWand = wandInHand.copy(slots = wandInHand.slots.replace(updatedSlot))
        return Result.success(oldState.updateCurrentRun(activeWands = oldState.currentRun.activeWands.replace(updatedWand)))
    }

    private fun removeSpellFromWandInLoot(wandId: WandId, oldState: GameState): Result<GameState> {
        val wandInLoot = oldState.currentRun.wandsInBag.findWand(wandId)
        check(wandInLoot != null) { "Could not find wand to remove spell from" }
        val slotToChange = wandInLoot.slots.singleOrNull { it.id == slotId }
        check(slotToChange != null) { "Could not find slot to remove spell from" }
        val updatedSlot = slotToChange.copy(spell = null)
        val updatedWand = wandInLoot.copy(slots = wandInLoot.slots.replace(updatedSlot))
        return Result.success(
            oldState.updateCurrentRun(
                wandsInBag = oldState.currentRun.wandsInBag.replace(updatedWand)
            )
        )
    }
}
