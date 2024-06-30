package de.moyapro.colors.game.actions.loot

import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.gameState.*
import de.moyapro.colors.util.*

data class AddWandToLootAction(val wand: Wand) : GameAction("Add wand to loot") {
    override val randomSeed: Int = -1

    override fun apply(oldState: NewGameState): Result<NewGameState> {
        require(!oldState.currentRun.wandsInBag.contains(wand)) { "Wand is already in loot" }
        val updatedWand = wand.copy(mageId = null)
        return Result.success(
            oldState.updateCurrentRun(
                wandsInBag = oldState.currentRun.wandsInBag.replace(updatedWand)
            )
        )
    }

}
