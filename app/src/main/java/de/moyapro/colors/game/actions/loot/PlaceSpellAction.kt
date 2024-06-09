package de.moyapro.colors.game.actions.loot

import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.util.*

data class PlaceSpellAction(val wandId: WandId, val slotId: SlotId, val spell: Spell) :
    GameAction("Place spell") {
    override val randomSeed: Int = this.hashCode()

    override fun apply(oldState: MyGameState): Result<MyGameState> {
        val (wandToUpdate, wandLocation) = findWand(oldState, wandId)
        check(wandToUpdate.slots.singleOrNull { slot -> slot.id == slotId } != null) { "Could not find slotId in wand when placing spell" }
        val updatedWand = placeSpellInWand(wandToUpdate, slotId, spell)
        return Result.success(
            when (wandLocation) {
                WandLocation.HAND -> oldState.copy(wands = oldState.wands.replace(updatedWand))
                WandLocation.LOOT -> oldState.copy(loot = oldState.loot.copy(wands = oldState.loot.wands.replace(updatedWand)))
            }
        )
    }

    private fun placeSpellInWand(wandToUpdate: Wand, slotId: SlotId, spell: Spell): Wand {
        return wandToUpdate.copy(
            slots = wandToUpdate.slots.mapIf({ slot -> slot.id == slotId }) { slotToUpdate -> slotToUpdate.copy(spell = spell) }
        )
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

    private fun findWand(oldState: MyGameState, wandId: WandId): Pair<Wand, WandLocation> {
        val wandInHand = oldState.findWand(wandId)
        val wandInLoot = oldState.loot.findWand(wandId)
        val hasWandInHand = wandInHand != null
        val hasWandInLoot = wandInLoot != null
        check(!(hasWandInHand && hasWandInLoot)) { "Found wand in hand AND loot" }
        val foundWand = wandInHand ?: wandInLoot
        val foundLocation = if (hasWandInHand) WandLocation.HAND else WandLocation.LOOT
        check(foundWand != null) { "Could not find wand to place spell into" }

        return Pair(foundWand, foundLocation)
    }

    private enum class WandLocation {
        HAND, LOOT
    }
}