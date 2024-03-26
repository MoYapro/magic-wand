package de.moyapro.colors.game.actions

import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.util.mapIf

data class ShowTargetSelectionAction(val originalAction: GameAction) :
    GameAction("Show target selection") {
    override val randomSeed: Int = this.hashCode()

    override fun apply(oldState: MyGameState): Result<MyGameState> {
        return Result.success(
            oldState.copy(
                enemies = oldState.enemies
                    .mapIf({ enemy -> originalAction.isValidTarget(enemy) }) { enemy ->
                        enemy.copy(
                            showTarget = true
                        )
                    }
            )
        )
    }
}
