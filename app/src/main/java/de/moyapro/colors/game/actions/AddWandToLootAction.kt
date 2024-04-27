package de.moyapro.colors.game.actions

import de.moyapro.colors.game.*
import de.moyapro.colors.takeTwo.*

data class AddWandToLootAction(val wand: Wand) : GameAction("Add wand to loot") {
    override val randomSeed: Int = -1

    override fun apply(oldState: MyGameState): Result<MyGameState> {
        require(!oldState.loot.wands.contains(wand)) { "Wand is already in loot" }
        val updatedWand = wand.copy(mageId = null)
        return Result.success(oldState.copy(loot = oldState.loot.copy(wands = oldState.loot.wands + updatedWand)))
    }

}
