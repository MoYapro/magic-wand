package de.moyapro.colors.game.actions

import de.moyapro.colors.game.model.gameState.GameState


data class NoOp(val nothing: Unit = Unit) : GameAction("NoOp") {
    override val randomSeed = -1

    override fun apply(oldState: GameState): Result<GameState> = Result.success(oldState)

    override fun equals(other: Any?): Boolean {
        return other is NoOp
    }

    override fun hashCode(): Int {
        return -1
    }
}
