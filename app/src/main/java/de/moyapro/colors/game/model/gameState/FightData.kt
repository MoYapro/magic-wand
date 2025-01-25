package de.moyapro.colors.game.model.gameState

import de.moyapro.colors.game.generators.Initializer
import de.moyapro.colors.game.model.Mage
import de.moyapro.colors.game.model.Magic
import de.moyapro.colors.game.model.MagicGenerator
import de.moyapro.colors.game.model.Wand
import de.moyapro.colors.game.model.interfaces.HasMages
import de.moyapro.colors.game.model.interfaces.HasWands
import de.moyapro.colors.util.FightState

data class FightData(
    val currentTurn: Int,
    val fightState: FightState,
    val battleBoard: BattleBoard,
    override val mages: List<Mage>,
    override val wands: List<Wand>,
    val magicToPlay: List<Magic>,
    val generators: List<MagicGenerator>,
) : HasWands, HasMages


fun notStartedFight() = FightData(
    currentTurn = 0,
    fightState = FightState.NOT_STARTED,
    battleBoard = Initializer.initialBattleBoard(),
    mages = emptyList(),
    wands = emptyList(),
    magicToPlay = emptyList(),
    generators = emptyList(),
)