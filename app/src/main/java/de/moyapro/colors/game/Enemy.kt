package de.moyapro.colors.game

import de.moyapro.colors.game.actions.EnemyAction
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.actions.NoOp
import de.moyapro.colors.takeTwo.EnemyId

data class Enemy(
    val id: EnemyId = EnemyId(),
    val health: Int,
    val possibleActions: List<EnemyAction<*>>,
    val nextAction: GameAction = NoOp(),
    val showTarget: Boolean = false
)
