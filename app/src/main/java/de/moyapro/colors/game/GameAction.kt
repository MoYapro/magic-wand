package de.moyapro.colors.game

interface GameAction {
    fun apply(oldState: MyGameState): MyGameState
}
