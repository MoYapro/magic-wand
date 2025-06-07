package de.moyapro.colors.game.actions.fight

import android.util.Log
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.generators.Initializer
import de.moyapro.colors.game.model.gameState.FightData
import de.moyapro.colors.game.model.gameState.GameState
import de.moyapro.colors.util.FightState.*

data class StartFightAction(override val randomSeed: Int = 1) : GameAction("Start fight Action") {
    private val TAG = "StartFightAction"
    private val initialBoardConfiguration = Initializer.initialBattleBoard()

    override fun apply(oldState: GameState): Result<GameState> {
        Log.d(TAG, "apply StartFightAction")
        require(oldState.currentFight.fightState != ONGOING) { "Fight already started. Current state was: ${oldState.currentFight.fightState}" }
        if (oldState.currentRun.mages.size < 3) return Result.failure(IllegalArgumentException("Cannot start fight with less than 3 mages"))
        val newFightData = FightData(
            currentTurn = 1,
            fightState = ONGOING,
            battleBoard = initialBoardConfiguration,
            mages = oldState.currentRun.mages,
            wands = oldState.currentRun.activeWands,
            magicToPlay = emptyList(),
            generators = oldState.currentRun.generators,
        )
        val newState = oldState.copy(currentFight = newFightData)
        return Result.success(newState)
    }

    override fun onAddAction(actions: MutableList<GameAction>) {
        // fight is added when rendering fightActivity. But sometimes it's rendered multiple times before applied so we just skip the additional adding here
        if (actions.any { it is StartFightAction }) {
            Log.w(TAG, "Cannot add multiple StartFightActions")
        } else {
            super.onAddAction(actions)
        }
    }
}