package de.moyapro.colors.game.actions

import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.takeTwo.SlotId
import de.moyapro.colors.takeTwo.WandId
import de.moyapro.colors.util.replace
import de.moyapro.colors.wand.Spell

data class PlaceSpellAction(val wandId: WandId, val slotId: SlotId, val spell: Spell) :
    GameAction("Place spell") {
    override val randomSeed: Int = this.hashCode()

    override fun apply(oldState: MyGameState): Result<MyGameState> {
        val updatedSpellsInStash = oldState.spellsInStash.filter { it != spell }
        check(updatedSpellsInStash.size != oldState.spellsInStash.size) { "Could not find spell to add in stash" }
        val wandToChange = oldState.findWand(wandId)
        val slotToChange = wandToChange?.slots?.firstOrNull { it.id == slotId }
        check(slotToChange != null) { "Could not find slot to change" }
        val spellFromSlotBefore: List<Spell> =
            if (slotToChange.spell == null) listOf() else listOf(slotToChange.spell)
        val updatedSlot = slotToChange.copy(spell = spell)
        val updatedWand =
            wandToChange.copy(slots = wandToChange.slots.replace(updatedSlot))
        val updatedWands = oldState.wands.replace(updatedWand)


        return Result.success(
            oldState.copy(
                spellsInStash = updatedSpellsInStash + spellFromSlotBefore,
                wands = updatedWands,
            )
        )
    }
}