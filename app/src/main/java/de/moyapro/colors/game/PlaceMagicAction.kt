package de.moyapro.colors.game

import de.moyapro.colors.takeTwo.SlotId
import de.moyapro.colors.takeTwo.Wand
import de.moyapro.colors.takeTwo.WandId
import de.moyapro.colors.util.replace
import de.moyapro.colors.wand.Magic

data class PlaceMagicAction(
    val wandId: WandId,
    val slotId: SlotId,
    val magicToPlace: Magic
) : GameAction {

    override fun apply(oldState: MyGameState): Result<MyGameState> {
        val targetWandWithMagic = updateWands(oldState).onFailure { return Result.failure(it) }
        val updatedMagicToPlay = oldState.magicToPlay.filter { magic -> magic != magicToPlace }
        return Result.success(
            MyGameState(
                enemies = oldState.enemies,
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
