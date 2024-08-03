package de.moyapro.colors.game.model.gameState

import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.interfaces.*
import de.moyapro.colors.util.*

data class FightData(
    val currentTurn: Int,
    val fightState: FightState,
    val battleBoard: BattleBoard,
    override val mages: List<Mage>,
    override val wands: List<Wand>,
    val magicToPlay: List<Magic>,
) : HasWands, HasMages
