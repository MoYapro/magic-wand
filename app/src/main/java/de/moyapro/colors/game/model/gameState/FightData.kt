package de.moyapro.colors.game.model.gameState

import de.moyapro.colors.game.model.*

data class FightData(
    val battlefield: BattleBoard,
    val mages: List<Mage>,
    val wands: List<Wand>,
)
