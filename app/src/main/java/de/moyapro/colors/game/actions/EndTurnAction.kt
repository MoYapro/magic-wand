package de.moyapro.colors.game.actions

import de.moyapro.colors.game.Enemy
import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.game.flatMap
import de.moyapro.colors.wand.Magic
import de.moyapro.colors.wand.MagicType
import kotlin.random.Random

class EndTurnAction() : GameAction {

    override val name: String = "End turn"
    override fun apply(oldState: MyGameState): Result<MyGameState> {
        val foldingFunction = { state: Result<MyGameState>, action: GameAction ->
            state.flatMap { action.apply(it) }
        }
        val result = oldState.enemies
            .map(Enemy::nextAction)
            .fold(Result.success(oldState), foldingFunction)
        return result.map(this::prepareNextTurn)
    }


    private fun prepareNextTurn(gameState: MyGameState): MyGameState {
        return gameState.copy(
            currentTurn = gameState.currentTurn + 1,
            enemies = calculateEnemyTurn(gameState),
            magicToPlay = refreshMagicToPlay(gameState.magicToPlay)
        )
    }

    private fun refreshMagicToPlay(leftOverMagic: List<Magic>): List<Magic> {
        return leftOverMagic +  (1..Random.nextInt(1, 2)).map { Magic(type = MagicType.values().random()) }
    }

    private fun calculateEnemyTurn(gameState: MyGameState): List<Enemy> {
        return gameState.enemies.map { enemy ->
            val nextAction = enemy.possibleActions.random()
            val nextGameAction = nextAction.init(enemy.id, gameState)
            enemy.copy(nextAction = nextGameAction)
        }
    }
}
