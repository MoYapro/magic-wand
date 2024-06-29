package de.moyapro.colors.game.actions

import de.moyapro.colors.game.model.gameState.*

object IncreaseActionCounterAction : GameAction("Increase action counter") {
    override val randomSeed: Int = -1

    override fun apply(oldState: NewGameState): Result<NewGameState> {

        return Result.success(
            oldState.updateCurrentFight(
                currentTurn = oldState.currentFight.currentTurn + 1,
                battlefield = oldState.currentFight.battleBoard,
                fightHasEnded = oldState.currentFight.fightHasEnded,
                magicToPlay = oldState.currentFight.magicToPlay,
                mages = oldState.currentFight.mages,
                wands = oldState.currentFight.wands,
            )
        )
    }
}
