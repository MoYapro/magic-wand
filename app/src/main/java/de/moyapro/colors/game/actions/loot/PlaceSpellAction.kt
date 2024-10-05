package de.moyapro.colors.game.actions.loot

import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.accessor.*
import de.moyapro.colors.game.model.gameState.*
import de.moyapro.colors.util.*

data class PlaceSpellAction(val wandId: WandId, val slotId: SlotId, val spell: Spell<*>) :
    GameAction("Place spell") {
    override val randomSeed: Int = this.hashCode()

    override fun apply(oldState: GameState): Result<GameState> {
        val (wandToUpdate, wandLocation) = findWand(oldState, wandId)
        check(wandToUpdate.slots.singleOrNull { slot -> slot.id == slotId } != null) { "Could not find slotId in wand when placing spell" }
        val updatedWand = placeSpellInWand(wandToUpdate, slotId, spell)
        return Result.success(
            when (wandLocation) {
                WandLocation.HAND -> oldState.updateCurrentRun(activeWands = oldState.currentRun.activeWands.replace(updatedWand))
                WandLocation.LOOT -> oldState.updateCurrentRun(wandsInBag = oldState.currentRun.wandsInBag.replace(updatedWand))
            }
        )
    }

    private fun placeSpellInWand(wandToUpdate: Wand, slotId: SlotId, spell: Spell<*>): Wand {
        return wandToUpdate.copy(
            slots = wandToUpdate.slots.mapIf({ slot -> slot.id == slotId }) { slotToUpdate -> slotToUpdate.copy(spell = spell) }
        )
    }

    private fun findWand(oldState: GameState, wandId: WandId): Pair<Wand, WandLocation> {
        val wandInHand = oldState.currentRun.activeWands.findWand(wandId)
        val wandInLoot = oldState.currentRun.wandsInBag.findWand(wandId)
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