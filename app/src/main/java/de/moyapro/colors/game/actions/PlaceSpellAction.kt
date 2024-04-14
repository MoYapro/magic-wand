package de.moyapro.colors.game.actions

import de.moyapro.colors.game.*
import de.moyapro.colors.takeTwo.*
import de.moyapro.colors.util.*
import de.moyapro.colors.wand.*

data class PlaceSpellAction(val wandId: WandId, val slotId: SlotId, val spell: Spell) :
    GameAction("Place spell") {
    override val randomSeed: Int = this.hashCode()

    override fun apply(oldState: MyGameState): Result<MyGameState> {
        return if (oldState.loot.spells.contains(spell)) spellFromStash(oldState)
        else spellFromWand(oldState)
    }

    private fun spellFromWand(oldState: MyGameState): Result<MyGameState> {
        val sourceSlot = oldState.findSlot(spell.id)
        val targetSlot = oldState.findSlot(slotId)
        check(sourceSlot != null) { "Could not find slot to take spell from" }
        check(targetSlot != null) { "Could not find slot to place spell" }
        if (targetSlot.spell == spell) return Result.success(oldState)
        val updatedTargetSlot = targetSlot.copy(spell = spell)
        val updatedSourceSlot = sourceSlot.copy(spell = targetSlot.spell)
        val sourceWand = oldState.findWand(updatedSourceSlot.id)
        val targetWand = oldState.findWand(updatedTargetSlot.id)
        check(sourceWand != null) { "Could not find wand to take spell from" }
        check(targetWand != null) { "Could not find wand to place spell into" }
        val replacedWands: List<Wand> = if (sourceWand.id == targetWand.id) {
            listOf(
                sourceWand.copy(
                    slots = sourceWand.slots.replace(updatedSourceSlot).replace(updatedTargetSlot)
                )
            )
        } else {
            listOf(
                sourceWand.copy(slots = sourceWand.slots.replace(updatedSourceSlot)),
                targetWand.copy(slots = targetWand.slots.replace(updatedTargetSlot)),
            )

        }
        var updatedWandList: List<Wand> = oldState.wands
        for (updatedWand in replacedWands) {
            updatedWandList = updatedWandList.replace(updatedWand)
        }
        return Result.success(oldState.copy(wands = updatedWandList))
    }

    private fun spellFromStash(oldState: MyGameState): Result<MyGameState> {
        val updatedLootSpells = oldState.loot.spells.filter { it != spell }
        check(updatedLootSpells.size != oldState.loot.spells.size) { "Could not find spell to add in stash" }
        val wandToChange = oldState.findWand(wandId)
        val targetSlot = wandToChange?.slots?.firstOrNull { it.id == slotId }
        check(targetSlot != null) { "Could not find slot to change" }
        val spellFromSlotBefore: List<Spell> =
            if (targetSlot.spell == null) listOf() else listOf(targetSlot.spell)
        val updatedSlot = targetSlot.copy(spell = spell)
        val updatedWand =
            wandToChange.copy(slots = wandToChange.slots.replace(updatedSlot))
        val updatedWands = oldState.wands.replace(updatedWand)

        return Result.success(
            oldState.copy(
                loot = oldState.loot.copy(spells = updatedLootSpells + spellFromSlotBefore),
                wands = updatedWands,
            )
        )
    }
}
