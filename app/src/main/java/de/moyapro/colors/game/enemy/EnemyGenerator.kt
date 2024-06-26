package de.moyapro.colors.game.enemy

import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.enemy.actions.*
import kotlin.random.*

class EnemyGenerator(seed: Int, val level: Int) {
    private val random = Random(seed)

    fun generateEnemy(): Enemy {
        return Enemy(
            health = random.nextInt(3 * level..4 * level),
            nextAction = NoOp(),
            possibleActions = listOf(SelfHealEnemyAction())
        )
    }

    // Zyktox -> likes poison
    // Firstix -> immune to fire
    // Zhulor -> undead
    // Oceor -> can breathe underwater

}