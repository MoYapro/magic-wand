package de.moyapro.colors.game.actions

import de.moyapro.colors.game.MyGameState

abstract class GameAction(
    val name: String,
) {
    abstract val randomSeed: Int
    abstract fun apply(oldState: MyGameState): Result<MyGameState>

}
