package de.moyapro.colors.game.actions.fight

import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.model.gameState.GameState
import de.moyapro.colors.util.FightState

data class LoseFightAction(override val randomSeed: Int = 1) : GameAction("Lose fight Action") {


    override fun apply(oldState: GameState): Result<GameState> {
        val newState = oldState.updateCurrentFight(fightState = FightState.LOST)
        return Result.success(newState)
    }

}