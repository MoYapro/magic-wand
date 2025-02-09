package de.moyapro.colors.game.actions.stash

import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.model.SlotId
import de.moyapro.colors.game.model.Wand
import de.moyapro.colors.game.model.WandId
import de.moyapro.colors.game.model.accessor.findWand
import de.moyapro.colors.game.model.gameState.GameState
import de.moyapro.colors.util.replace

data class RemoveSpellFromWandAction(
    val wandId: WandId,
    val slotId: SlotId,
) :
    GameAction("Remove spell from wand") {
    override val randomSeed: Int = this.hashCode()

    override fun apply(oldState: GameState): Result<GameState> {
        val wandInHand = oldState.currentRun.activeWands.findWand(wandId)
        return if (wandInHand != null) removeSpellFromWandInHand(wandInHand, oldState)
        else removeSpellFromWandInStash(wandId, oldState)
    }

    private fun removeSpellFromWandInHand(wandInHand: Wand, oldState: GameState): Result<GameState> {
        val slotToChange = wandInHand.slots.singleOrNull { it.id == slotId }
        check(slotToChange != null) { "Could not find slot to remove spell from" }
        val updatedSlot = slotToChange.copy(spell = null)
        val updatedWand = wandInHand.copy(slots = wandInHand.slots.replace(updatedSlot))
        return Result.success(oldState.updateCurrentRun(activeWands = oldState.currentRun.activeWands.replace(updatedWand)))
    }

    private fun removeSpellFromWandInStash(wandId: WandId, oldState: GameState): Result<GameState> {
        val wandInStash = oldState.currentRun.wandsInBag.findWand(wandId)
        check(wandInStash != null) { "Could not find wand to remove spell from" }
        val slotToChange = wandInStash.slots.singleOrNull { it.id == slotId }
        check(slotToChange != null) { "Could not find slot to remove spell from" }
        val updatedSlot = slotToChange.copy(spell = null)
        val updatedWand = wandInStash.copy(slots = wandInStash.slots.replace(updatedSlot))
        return Result.success(
            oldState.updateCurrentRun(
                wandsInBag = oldState.currentRun.wandsInBag.replace(updatedWand)
            )
        )
    }
}
