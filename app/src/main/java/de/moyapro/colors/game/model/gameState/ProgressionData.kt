package de.moyapro.colors.game.model.gameState

import de.moyapro.colors.game.enemy.Enemy
import de.moyapro.colors.game.model.Spell
import de.moyapro.colors.game.model.Wand

data class ProgressionData(
    val unlockedSpells: List<Spell<*>>,
    val unlockedWands: List<Wand>,
    val unlockedEnemies: List<Enemy>,
    val achievements: List<Achievement>,
)
