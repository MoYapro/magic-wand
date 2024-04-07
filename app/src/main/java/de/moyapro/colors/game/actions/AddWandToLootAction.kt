package de.moyapro.colors.game.actions

import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.takeTwo.Wand

data class AddWandToLootAction(val wand: Wand) : GameAction("Add wand to loot") {
    override val randomSeed: Int = -1

    override fun apply(oldState: MyGameState): Result<MyGameState> {
        check(!oldState.loot.wands.contains(wand)) { "Wand is already in loot" }
        return Result.success(oldState.copy(loot = oldState.loot.copy(wands = oldState.loot.wands + wand)))
    }

}
