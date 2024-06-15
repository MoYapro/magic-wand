package de.moyapro.colors.game.model.gameState

data class NewGameState(
    val currentFight: FightData,
    val currentRun: RunData,
    val progression: ProgressionData,
    val options: GameOptions,
)