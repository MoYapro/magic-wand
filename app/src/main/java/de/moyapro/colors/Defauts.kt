package de.moyapro.colors

import de.moyapro.colors.game.model.gameState.*
import de.moyapro.colors.util.*

fun initDefaultOptions() = GameOptions(thisIsAnOption = true)

fun initEmptyProgression() = ProgressionData(
    unlockedWands = emptyList(),
    achievements = emptyList(),
    unlockedSpells = emptyList(),
    unlockedEnemies = emptyList()
)

fun initEmptyFight(): FightData {
    return FightData(
        currentTurn = 0,
        fightState = FightState.NOT_STARTED,
        battleBoard = BattleBoard(fields = emptyList()),
        mages = emptyList(),
        magicToPlay = emptyList(),
        wands = emptyList(),
        generator = emptyList(),
    )
}

fun initEmptyRun(): RunData {
    return RunData(
        mages = emptyList(),
        activeWands = emptyList(),
        spells = emptyList(),
        wandsInBag = emptyList(),
        generators = emptyList(),
    )
}
