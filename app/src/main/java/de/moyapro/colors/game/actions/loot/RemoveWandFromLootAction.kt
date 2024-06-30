package de.moyapro.colors.game.actions.loot

import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.gameState.*

data class RemoveWandFromLootAction(val wand: Wand) : GameAction("Remove wand from loot") {
    override val randomSeed: Int = -1

    override fun apply(oldState: NewGameState): Result<NewGameState> {
        require(oldState.currentRun.wandsInBag.map(Wand::id).contains(wand.id)) { "Could not find wand to remove in loot" }
        return Result.success(oldState.updateCurrentRun(wandsInBag = oldState.currentRun.wandsInBag.filter { it.id != wand.id }))
    }

}
