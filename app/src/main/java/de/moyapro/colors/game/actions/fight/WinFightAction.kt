package de.moyapro.colors.game.actions.fight

import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.model.gameState.*
import de.moyapro.colors.util.*

data class WinFightAction(override val randomSeed: Int = 1) : GameAction("Lose fight Action") {


    override fun apply(oldState: GameState): Result<GameState> {
        val newState = oldState.updateCurrentFight(fightState = FightState.WIN)
        return Result.success(newState)
    }

}