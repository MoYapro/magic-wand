package de.moyapro.colors.game.enemy

import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.actions.NoOp
import de.moyapro.colors.game.effect.Effect
import de.moyapro.colors.game.enemy.actions.EnemyAction
import de.moyapro.colors.game.model.DirectionalImage
import de.moyapro.colors.game.model.EnemyId
import de.moyapro.colors.game.model.interfaces.HasId

data class Enemy(
    override val id: EnemyId = EnemyId(),
    val name: String = "Enemy",
    val health: Int,
    val breadth: Int = 1,
    val size: Int = 1,
    val power: Int = 0,
    val possibleActions: List<EnemyAction<*>> = emptyList(),
    val nextAction: GameAction = NoOp(),
    val statusEffects: Map<Effect, Int> = emptyMap(),
    val image: DirectionalImage? = null,
) : HasId<EnemyId>
