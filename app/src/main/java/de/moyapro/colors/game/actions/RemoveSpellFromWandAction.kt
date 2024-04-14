package de.moyapro.colors.game.actions

import de.moyapro.colors.game.*
import de.moyapro.colors.takeTwo.*
import de.moyapro.colors.util.*
import de.moyapro.colors.wand.*

data class RemoveSpellFromWandAction(
    val wandId: WandId,
    val slotId: SlotId,
    val spell: Spell,
) :
    GameAction("Remove spell from wand") {
    override val randomSeed: Int = this.hashCode()

    override fun apply(oldState: MyGameState): Result<MyGameState> {
        val wandInHand = oldState.findWand(wandId)
        return if (wandInHand != null) removeSpellFromWandInHand(wandInHand, oldState)
        else removeSpellFromWandInLoot(wandId, oldState)
    }

    private fun removeSpellFromWandInHand(wandInHand: Wand, oldState: MyGameState): Result<MyGameState> {
        val slotToChange = wandInHand.slots.singleOrNull { it.id == slotId }
        check(slotToChange != null) { "Could not find slot to remove spell from" }
        val updatedSlot = slotToChange.copy(spell = null)
        val updatedWand = wandInHand.copy(slots = wandInHand.slots.replace(updatedSlot))
        return Result.success(oldState.copy(wands = oldState.wands.replace(updatedWand)))
    }

    private fun removeSpellFromWandInLoot(wandId: WandId, oldState: MyGameState): Result<MyGameState> {
        val wandInLoot = oldState.loot.findWand(wandId)
        check(wandInLoot != null) { "Could not find wand to remove spell from" }
        val slotToChange = wandInLoot.slots.singleOrNull { it.id == slotId }
        check(slotToChange != null) { "Could not find slot to remove spell from" }
        val updatedSlot = slotToChange.copy(spell = null)
        val updatedWand = wandInLoot.copy(slots = wandInLoot.slots.replace(updatedSlot))
        return Result.success(
            oldState.copy(
                loot = oldState.loot.copy(
                    wands = oldState.loot.wands.replace(updatedWand)
                )
            )
        )
    }
}
