package de.moyapro.colors.game.actions.fight

import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.util.*

data class PlaceMagicAction(
    val wandId: WandId,
    val slotId: SlotId,
    val magicToPlace: Magic,
) : GameAction("Place Magic") {

    override val randomSeed = this.hashCode()

    override fun apply(oldState: MyGameState): Result<MyGameState> {
        val targetWandWithMagic = updateWands(oldState).onFailure { return Result.failure(it) }
        val updatedMagicToPlay = oldState.magicToPlay.filter { magic -> magic != magicToPlace }
        check(updatedMagicToPlay.size + 1 == oldState.magicToPlay.size) { "Not exactly one magic was used when placing magic" }
        return Result.success(
            oldState.copy(
                wands = oldState.wands.replace(wandId, targetWandWithMagic.getOrThrow()),
                magicToPlay = updatedMagicToPlay
            )
        )
    }

    private fun updateWands(oldState: MyGameState): Result<Wand> {
        val targetWand: Wand = oldState.findWand(wandId)
            ?: return Result.failure(IllegalStateException("Could not find wand with id $wandId"))
        return targetWand.putMagic(slotId, magicToPlace)
    }

}
