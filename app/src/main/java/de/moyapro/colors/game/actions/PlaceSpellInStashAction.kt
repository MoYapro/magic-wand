package de.moyapro.colors.game.actions

import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.takeTwo.SpellId
import de.moyapro.colors.util.replace

data class PlaceSpellInStashAction(val spellId: SpellId) :
    GameAction("Place spell") {
    override val randomSeed: Int = this.hashCode()

    override fun apply(oldState: MyGameState): Result<MyGameState> {
        val wandToChange = oldState.wands.singleOrNull { wand ->
            wand.slots.any { slot -> slot.spell?.id == spellId }
        }
        check(wandToChange != null) { "Could not find wand to remove spell from" }
        val slotToChange = wandToChange.slots.single { slot -> slot.spell?.id == spellId }
        val spellToPlaceInStash = slotToChange.spell
        check(spellToPlaceInStash != null) { "Could not find spell to remove from wand" }
        val updatedSpellsInStash = oldState.spellsInStash + spellToPlaceInStash

        val updatedSlot = slotToChange.copy(spell = null)
        val updatedWand =
            wandToChange.copy(slots = wandToChange.slots.replace(updatedSlot))
        val updatedWands = oldState.wands.replace(updatedWand)


        return Result.success(
            oldState.copy(
                spellsInStash = updatedSpellsInStash,
                wands = updatedWands,
            )
        )
    }
}