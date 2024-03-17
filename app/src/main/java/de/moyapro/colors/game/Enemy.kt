package de.moyapro.colors.game

import de.moyapro.colors.game.actions.EnemyAction
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.actions.NoOp
import de.moyapro.colors.takeTwo.EnemyId
import de.moyapro.colors.takeTwo.HasId

data class Enemy(
    override val id: EnemyId = EnemyId(),
    val health: Int,
    val possibleActions: List<EnemyAction<*>>,
    val nextAction: GameAction = NoOp(),
    val showTarget: Boolean = false
): HasId<EnemyId>
