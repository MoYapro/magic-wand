package de.moyapro.colors.game.model.gameState

import de.moyapro.colors.game.model.*

data class RunData(
    val mages: List<Mage>,
    val spells: List<Spell>,
    val activeWands: List<Wand>,
    val wandsInBag: List<Wand>,
)
