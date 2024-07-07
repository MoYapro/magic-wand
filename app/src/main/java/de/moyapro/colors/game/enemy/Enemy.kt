package de.moyapro.colors.game.enemy

import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.enemy.actions.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.interfaces.*

data class Enemy(
    override val id: EnemyId = EnemyId(),
    val name: String = "Enemy",
    val health: Int,
    val possibleActions: List<EnemyAction<*>>,
    val nextAction: GameAction = NoOp(),
    val showTarget: Boolean = false,
) : HasId<EnemyId>
