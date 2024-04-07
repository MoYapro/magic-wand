package de.moyapro.colors.game.actions

import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.takeTwo.Wand

data class RemoveWandFromLootAction(val wand: Wand) : GameAction("Remove wand from loot") {
    override val randomSeed: Int = -1

    override fun apply(oldState: MyGameState): Result<MyGameState> {
        check(oldState.loot.wands.contains(wand)) { "Could not find wand to remove in loot" }
        return Result.success(oldState.copy(loot = oldState.loot.copy(wands = oldState.loot.wands.filter { it.id != wand.id })))
    }

}
