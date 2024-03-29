package de.moyapro.colors.game

import de.moyapro.colors.createExampleEnemy
import de.moyapro.colors.createExampleMagic
import de.moyapro.colors.createExampleWand
import de.moyapro.colors.takeTwo.Mage

object StartFightFactory {
    fun createInitialState(): MyGameState {
        val mages = listOf(Mage(health = 5))
        val wands = mages.map { mage -> createExampleWand(mage.id) }
        return MyGameState(
            enemies = listOf(createExampleEnemy(), createExampleEnemy()),
            wands = wands,
            magicToPlay = listOf(createExampleMagic()),
            currentTurn = 0,
            mages = mages,
        )
    }

}
