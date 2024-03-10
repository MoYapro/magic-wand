package de.moyapro.colors.game.actions

import de.moyapro.colors.game.Enemy
import de.moyapro.colors.takeTwo.EnemyId

interface RequiresTargetAction {

    val target: EnemyId?
    fun isValidTarget(enemy: Enemy): Boolean
    fun withSelection(target: Enemy): GameAction
}
