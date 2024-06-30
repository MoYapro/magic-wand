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
        currentTurn: Int = this.currentFight.currentTurn,
        fightHasEnded: FightOutcome = this.currentFight.fightHasEnded,
        battlefield: BattleBoard = this.currentFight.battleBoard,
        mages: List<Mage> = this.currentFight.mages,
        wands: List<Wand> = this.currentFight.wands,
        magicToPlay: List<Magic> = this.currentFight.magicToPlay,
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

    fun updateCurrentRun(
        mages: List<Mage> = this.currentRun.mages,
        spells: List<Spell> = this.currentRun.spells,
        activeWands: List<Wand> = this.currentRun.activeWands,
        wandsInBag: List<Wand> = this.currentRun.wandsInBag,
    ): NewGameState = this.copy(
        currentRun = this.currentRun.copy(
            mages = mages,
            spells = spells,
            activeWands = activeWands,
            wandsInBag = wandsInBag
        )
    )
}