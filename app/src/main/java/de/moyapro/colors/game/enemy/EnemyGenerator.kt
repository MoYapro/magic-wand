package de.moyapro.colors.game.enemy

import de.moyapro.colors.game.actions.*
import kotlin.random.*

class EnemyGenerator(seed: Int) {
    fun generateEnemy(): Enemy {
        return Enemy(
            health = 1,
            nextAction = NoOp(),
            possibleActions = listOf(SelfHealEnemyAction())

        )
    }

    private val random = Random(seed)
}