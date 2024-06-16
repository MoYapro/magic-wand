package de.moyapro.colors.game.model.gameState

import de.moyapro.colors.game.model.*
import de.moyapro.colors.util.*

data class NewGameState(
    val currentFight: FightData,
    val currentRun: RunData,
    val progression: ProgressionData,
    val options: GameOptions,
) {

    fun updateCurrentFight(
        currentTurn: Int,
        fightHasEnded: FightOutcome,
        battlefield: BattleBoard,
        mages: List<Mage>,
        wands: List<Wand>,
        magicToPlay: List<Magic>,
    ): NewGameState = this.copy(
        currentFight = this.currentFight.copy(
            currentTurn = currentTurn,
            fightHasEnded = fightHasEnded,
            battleBoard = battlefield,
            mages = mages,
            wands = wands,
            magicToPlay = magicToPlay
        )
    )
}