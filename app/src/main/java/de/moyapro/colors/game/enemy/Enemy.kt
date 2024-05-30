package de.moyapro.colors.game.enemy

import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.enemy.actions.*
import de.moyapro.colors.takeTwo.*

data class Enemy(
    override val id: EnemyId = EnemyId(),
    val health: Int,
    val possibleActions: List<EnemyAction<*>>,
    val nextAction: GameAction = NoOp(),
    val showTarget: Boolean = false
): HasId<EnemyId>
