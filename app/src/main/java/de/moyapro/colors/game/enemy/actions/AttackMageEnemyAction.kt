package de.moyapro.colors.game.enemy.actions

import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.accessor.*
import de.moyapro.colors.game.model.gameState.*
import de.moyapro.colors.util.*
import kotlin.random.*

data class AttackMageEnemyAction(override val name: String = "Attack") : EnemyAction<MageId> {
    override val randomSeed = this.hashCode()
    private val random = Random(randomSeed)

    override fun init(self: EnemyId, gameState: GameState): GameAction {
        val target = selectTarget(gameState)
        return AttackMageAction(target.id)
    }

    private fun selectTarget(gameState: GameState): Mage {
        return gameState.currentFight.mages.random(random)
    }
}

data class AttackMageAction(val mageId: MageId) : GameAction("Attack ${symbolForMageId(mageId)}") {
    override val randomSeed = this.hashCode()

    override fun apply(oldState: GameState): Result<GameState> {
        val mage = oldState.currentFight.findMage(mageId)
        val updatedMage = mage.copy(health = mage.health - 1)
        return Result.success(
            oldState.updateCurrentFight(
                mages = oldState.currentFight.mages.replace(updatedMage)
            )
        )
    }


}

fun symbolForMageId(mageId: MageId): String {
    return when (mageId.id.toInt()) {
        0 -> "<"
        1 -> "v"
        2 -> ">"
        else -> throw IllegalArgumentException("MageId must be 0,1,2 but this was: $mageId")
    }
}
