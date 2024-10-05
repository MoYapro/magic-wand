package de.moyapro.colors.game.actions.fight

import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.generators.*
import de.moyapro.colors.game.model.gameState.*
import de.moyapro.colors.util.*
import de.moyapro.colors.util.FightState.ONGOING

data class StartFightAction(override val randomSeed: Int = 1) : GameAction("Start fight Action") {


    override fun apply(oldState: GameState): Result<GameState> {
        require(oldState.currentFight.fightState == FightState.NOT_STARTED) { "Fight already started" }
        if (oldState.currentRun.mages.size < 3) return Result.failure(IllegalArgumentException("Cannot start fight with less than 3 mages"))
        val newFightData = FightData(
            currentTurn = 1,
            fightState = ONGOING,
            battleBoard = Initializer.initialBattleBoard(),
            mages = oldState.currentRun.mages,
            wands = oldState.currentRun.activeWands,
            magicToPlay = emptyList()
        )
        val newState = oldState.copy(currentFight = newFightData)
        return Result.success(newState)
    }

}