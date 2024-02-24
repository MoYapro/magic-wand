package de.moyapro.colors.game

import de.moyapro.colors.util.replace
import de.moyapro.colors.wand.Gem
import de.moyapro.colors.wand.Magic
import de.moyapro.colors.wand.Wand
import java.util.UUID

data class PlaceGemAction(val wandId: UUID, val gem: Gem) : GameAction {

    override fun apply(oldState: MyGameState): MyGameState {
        val targetWand: Wand = oldState.findWand(wandId)!!
        val targetWandWithGem = targetWand.placeMagic(Magic()).wand
        return MyGameState(oldState.wands.replace(wandId, targetWandWithGem))
    }

}
