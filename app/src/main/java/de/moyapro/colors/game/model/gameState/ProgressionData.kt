package de.moyapro.colors.game.model.gameState

import de.moyapro.colors.game.enemy.*
import de.moyapro.colors.game.model.*

data class ProgressionData(
    val unlockedSpells: List<Spell<*>>,
    val unlockedWands: List<Wand>,
    val unlockedEnemies: List<Enemy>,
    val achievements: List<Achievement>,
)
