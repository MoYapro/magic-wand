package de.moyapro.colors.game.model.gameState

import de.moyapro.colors.game.generators.*
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
    val generator: List<MagicGenerator>,
) : HasWands, HasMages


fun notStartedFight() = FightData(
    currentTurn = 0,
    fightState = FightState.NOT_STARTED,
    battleBoard = Initializer.initialBattleBoard(),
    mages = emptyList(),
    wands = emptyList(),
    magicToPlay = emptyList(),
    generator = emptyList(),
)