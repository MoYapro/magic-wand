package de.moyapro.colors.game

import de.moyapro.colors.takeTwo.Wand

data class AddWandAction(val wand: Wand) : GameAction {

    override fun apply(oldState: MyGameState): MyGameState {
        return MyGameState(oldState.wands + wand)
    }

}
