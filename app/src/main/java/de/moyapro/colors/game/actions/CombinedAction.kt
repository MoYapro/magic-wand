package de.moyapro.colors.game.actions

import de.moyapro.colors.game.model.gameState.*

data class CombinedAction(val actions: List<GameAction>) : GameAction("Combined") {

    constructor(vararg actions: GameAction?) : this(actions.toList().filterNotNull())

    override val randomSeed: Int = this.hashCode()

    override fun apply(oldState: GameState): Result<GameState> {
        return actions.fold(Result.success(oldState), ::applyAllActions)
    }
}
