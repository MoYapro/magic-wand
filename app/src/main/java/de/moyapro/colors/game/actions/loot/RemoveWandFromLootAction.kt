package de.moyapro.colors.game.actions.loot

import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.model.*

data class RemoveWandFromLootAction(val wand: Wand) : GameAction("Remove wand from loot") {
    override val randomSeed: Int = -1

    override fun apply(oldState: MyGameState): Result<MyGameState> {
        require(oldState.loot.wands.map(Wand::id).contains(wand.id)) { "Could not find wand to remove in loot" }
        return Result.success(oldState.copy(loot = oldState.loot.copy(wands = oldState.loot.wands.filter { it.id != wand.id })))
    }

}
