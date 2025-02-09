package de.moyapro.colors.game.actions.stash

import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.model.SlotId
import de.moyapro.colors.game.model.Spell
import de.moyapro.colors.game.model.Wand
import de.moyapro.colors.game.model.WandId
import de.moyapro.colors.game.model.accessor.findWand
import de.moyapro.colors.game.model.gameState.GameState
import de.moyapro.colors.util.mapIf
import de.moyapro.colors.util.replace

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
                WandLocation.STASH -> oldState.updateCurrentRun(wandsInBag = oldState.currentRun.wandsInBag.replace(updatedWand))
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
        val wandInStash = oldState.currentRun.wandsInBag.findWand(wandId)
        val hasWandInHand = wandInHand != null
        val hasWandInStash = wandInStash != null
        check(!(hasWandInHand && hasWandInStash)) { "Found wand in hand AND stash" }
        val foundWand = wandInHand ?: wandInStash
        val foundLocation = if (hasWandInHand) WandLocation.HAND else WandLocation.STASH
        check(foundWand != null) { "Could not find wand to place spell into" }

        return Pair(foundWand, foundLocation)
    }

    private enum class WandLocation {
        HAND, STASH
    }
}