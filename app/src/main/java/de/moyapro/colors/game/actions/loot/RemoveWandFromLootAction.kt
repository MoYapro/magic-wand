package de.moyapro.colors.game.actions.loot

import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.model.Wand
import de.moyapro.colors.game.model.gameState.GameState

data class RemoveWandFromLootAction(val wandToRemove: Wand) : GameAction("Remove wand from loot") {
    override val randomSeed: Int = -1

    override fun apply(oldState: GameState): Result<GameState> {
        require(oldState.currentRun.wandsInBag.map(Wand::id).contains(wandToRemove.id)) { "Could not find wand to remove in loot" }
        val remainingWands = oldState.currentRun.wandsInBag.filter { it.id != wandToRemove.id }
        check(remainingWands.size < oldState.currentRun.wandsInBag.size) { "Did not remove any wands from ${oldState.currentRun.wandsInBag} when looking for $wandToRemove" }
        return Result.success(oldState.updateCurrentRun(wandsInBag = remainingWands))
    }

}
