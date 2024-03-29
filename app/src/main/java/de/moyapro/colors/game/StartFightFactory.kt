package de.moyapro.colors.game

import de.moyapro.colors.createExampleEnemy
import de.moyapro.colors.createExampleMagic
import de.moyapro.colors.createExampleWand
import de.moyapro.colors.takeTwo.Mage
import de.moyapro.colors.takeTwo.Wand

object StartFightFactory {
    fun createInitialState(wands: List<Wand>?): MyGameState {
        val mages = listOf(Mage(health = 5))
        val actualWands = wands ?: mages.map { mage -> createExampleWand(mage.id) }
        return MyGameState(
            enemies = listOf(createExampleEnemy(), createExampleEnemy()),
            wands = actualWands,
            magicToPlay = listOf(createExampleMagic()),
            currentTurn = 0,
            mages = mages,
        )
    }

}
