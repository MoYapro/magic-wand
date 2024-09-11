package de.moyapro.colors.game.enemy

import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.effect.*
import de.moyapro.colors.game.enemy.actions.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.interfaces.*

data class Enemy(
    override val id: EnemyId = EnemyId(),
    val name: String = "Enemy",
    val health: Int,
    val breadth: Int = 1,
    val size: Int = 1,
    val possibleActions: List<EnemyAction<*>> = emptyList(),
    val nextAction: GameAction = NoOp(),
    val statusEffects: Map<Effect, Int> = emptyMap(),
) : HasId<EnemyId>


fun TargetDummy(health: Int) = Enemy(name = "TargetDummy", health = health, possibleActions = emptyList())
