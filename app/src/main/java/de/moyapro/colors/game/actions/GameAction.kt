package de.moyapro.colors.game.actions

import de.moyapro.colors.game.MyGameState

interface GameAction {
    val name: String
    fun apply(oldState: MyGameState): Result<MyGameState>
}
