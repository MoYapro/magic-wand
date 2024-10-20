package de.moyapro.colors.game.model.gameState

import de.moyapro.colors.game.model.*
import de.moyapro.colors.util.*

data class GameState(
    val currentFight: FightData,
    val currentRun: RunData,
    val progression: ProgressionData,
    val options: GameOptions,
) {

    fun updateCurrentFight(
        currentTurn: Int = this.currentFight.currentTurn,
        fightState: FightState = this.currentFight.fightState,
        battlefield: BattleBoard = this.currentFight.battleBoard,
        mages: List<Mage> = this.currentFight.mages,
        wands: List<Wand> = this.currentFight.wands,
        magicToPlay: List<Magic> = this.currentFight.magicToPlay,
        generators: List<MagicGenerator> = this.currentFight.generators,
    ): GameState = this.copy(
        currentFight = this.currentFight.copy(
            currentTurn = currentTurn,
            fightState = fightState,
            battleBoard = battlefield,
            mages = mages,
            wands = wands,
            magicToPlay = magicToPlay,
            generators = generators,
        )
    )

    fun updateCurrentRun(
        mages: List<Mage> = this.currentRun.mages,
        spells: List<Spell<*>> = this.currentRun.spells,
        activeWands: List<Wand> = this.currentRun.activeWands,
        wandsInBag: List<Wand> = this.currentRun.wandsInBag,
        generators: List<MagicGenerator> = this.currentRun.generators,
    ): GameState = this.copy(
        currentRun = this.currentRun.copy(
            mages = mages,
            spells = spells,
            activeWands = activeWands,
            wandsInBag = wandsInBag,
            generators = generators,
        )
    )
}