package de.moyapro.colors.game.actions.fight

import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.takeTwo.*

data class TargetSelectedAction(override val target: EnemyId?) :
    GameAction("Target selected action") {
    override val randomSeed: Int = this.hashCode()

    override fun onAddAction(actions: MutableList<GameAction>) {
        check(actions.last() is ShowTargetSelectionAction) { "TargetSelectedAction has not showTargetSelectionAction predecessor" }
        check(target != null) { "TargetSelectedAction without target" }
        val showTargetSelectionAction = actions.removeLast() as ShowTargetSelectionAction
        actions.add(showTargetSelectionAction.originalAction.withSelection(target))
    }

    override fun apply(oldState: MyGameState): Result<MyGameState> {
        return Result.success(oldState.copy(
            enemies = oldState.enemies.map { enemy -> enemy.copy(showTarget = false) }
        ))
    }
}
