package de.moyapro.colors.game.actions.fight

import android.util.Log
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.model.gameState.GameState
import de.moyapro.colors.util.FightState

data class EndFightAction(override val randomSeed: Int = 1) : GameAction("End fight Action") {
    private val TAG = "EndFightAction"

    override fun apply(oldState: GameState): Result<GameState> {
        Log.d(TAG, "apply EndFightAction")
        val newState = oldState.updateCurrentFight(fightState = FightState.NOT_STARTED, currentTurn = 0)
        return Result.success(newState)
    }

}