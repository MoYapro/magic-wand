package de.moyapro.colors.game.enemy.actions

import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.takeTwo.*
import de.moyapro.colors.util.*
import kotlin.random.*

data class AttackEnemyAction(override val name: String = "Attack") : EnemyAction<MageId> {
    override val randomSeed = this.hashCode()
    private val random = Random(randomSeed)

    override fun init(self: EnemyId, gameState: MyGameState): GameAction {
        val target = selectTarget(gameState)
        return AttackMageAction(target.id)
    }

    private fun selectTarget(gameState: MyGameState): Mage {
        return gameState.mages[random.nextInt(gameState.mages.size)]
    }
}

data class AttackMageAction(val mageId: MageId) : GameAction("Attack player action") {
    override val randomSeed = this.hashCode()

    override fun apply(oldState: MyGameState): Result<MyGameState> {
        val mage = oldState.findMage(mageId)
        check(mage != null) { "Could not find mage to hit" }
        val updatedMage = mage.copy(health = mage.health - 1)
        return Result.success(
            oldState.copy(
                mages = oldState.mages.replace(updatedMage)
            )
        )
    }

}
