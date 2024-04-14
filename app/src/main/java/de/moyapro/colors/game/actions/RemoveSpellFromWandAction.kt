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
        val wandToChange = oldState.findWand(wandId)
        check(wandToChange != null) { "Could not find wand to remove spell from" }
        val slotToChange = wandToChange.slots.singleOrNull { it.id == slotId }
        check(slotToChange != null) { "Could not find slot to remove spell from" }
        val updatedSlot = slotToChange.copy(spell = null)
        val updatedWand = wandToChange.copy(slots = wandToChange.slots.replace(updatedSlot))
        return Result.success(oldState.copy(wands = oldState.wands.replace(updatedWand)))
    }
}
