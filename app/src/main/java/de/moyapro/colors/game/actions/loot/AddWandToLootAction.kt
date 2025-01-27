package de.moyapro.colors.game.actions.loot

import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.model.Wand
import de.moyapro.colors.game.model.gameState.GameState

data class AddWandToLootAction(val wand: Wand) : GameAction("Add wand to loot") {
    override val randomSeed: Int = -1

    override fun apply(oldState: GameState): Result<GameState> {
        require(!oldState.currentRun.wandsInBag.contains(wand)) { "Wand is already in loot" }
        val updatedWand = wand.copy(mageId = null)
        return Result.success(
            oldState.updateCurrentRun(
                wandsInBag = oldState.currentRun.wandsInBag + updatedWand
            )
        )
    }

}
