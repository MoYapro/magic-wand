package de.moyapro.colors.game.model.gameState

import de.moyapro.colors.game.model.Mage
import de.moyapro.colors.game.model.Magic
import de.moyapro.colors.game.model.MagicGenerator
import de.moyapro.colors.game.model.Spell
import de.moyapro.colors.game.model.Wand
import de.moyapro.colors.util.FightState

data class GameState(
    val currentFight: FightData,
    val currentRun: RunData,
    val progression: ProgressionData,
    val options: GameOptions,
) {

    fun updateCurrentFight(
        currentTurn: Int = this.currentFight.currentTurn,
        fightState: FightState = this.currentFight.fightState,
        battleBoard: BattleBoard = this.currentFight.battleBoard,
        mages: List<Mage> = this.currentFight.mages,
        wands: List<Wand> = this.currentFight.wands,
        magicToPlay: List<Magic> = this.currentFight.magicToPlay,
        generators: List<MagicGenerator> = this.currentFight.generators,
    ): GameState = this.copy(
        currentFight = this.currentFight.copy(
            currentTurn = currentTurn,
            fightState = fightState,
            battleBoard = battleBoard,
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