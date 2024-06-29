package de.moyapro.colors.game.actions

import de.moyapro.colors.game.model.gameState.*

class NoOp : GameAction("NoOp") {
    override val randomSeed = -1

    override fun apply(oldState: NewGameState): Result<NewGameState> = Result.success(oldState)
}
