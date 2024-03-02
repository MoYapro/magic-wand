package de.moyapro.colors.game

import de.moyapro.colors.takeTwo.SlotId
import de.moyapro.colors.takeTwo.Wand
import de.moyapro.colors.takeTwo.WandId
import de.moyapro.colors.util.replace
import de.moyapro.colors.wand.Magic

data class PlaceMagicAction(val wandId: WandId, val slotId: SlotId, val magic: Magic) : GameAction {

    override fun apply(oldState: MyGameState): Result<MyGameState> {
        val targetWand: Wand = oldState.findWand(wandId)
            ?: return Result.failure(IllegalStateException("Could not find wand with id $wandId"))
        val targetWandWithMagic = targetWand.putMagic(slotId, magic)
        return targetWandWithMagic.map { updatedWand ->
            MyGameState(oldState.wands.replace(wandId, updatedWand))
        }
    }

}
