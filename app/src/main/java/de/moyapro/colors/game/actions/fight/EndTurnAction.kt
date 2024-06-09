package de.moyapro.colors.game.actions.fight

import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.enemy.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.util.*
import de.moyapro.colors.util.FightOutcome.LOST
import de.moyapro.colors.util.FightOutcome.ONGOING
import de.moyapro.colors.util.FightOutcome.WIN
import kotlin.random.*


class EndTurnAction : GameAction("End turn") {

    override val randomSeed = this.hashCode()
    private lateinit var random: Random

    override fun apply(oldState: MyGameState): Result<MyGameState> {
        random = Random(randomSeed)
        val foldingFunction = { state: Result<MyGameState>, action: GameAction ->
            state.flatMap { action.apply(it) }
        }
        val result =
            oldState.enemies.map(Enemy::nextAction).fold(Result.success(oldState), foldingFunction)
        return result.map(this::prepareNextTurn)
    }


    private fun prepareNextTurn(gameState: MyGameState): MyGameState {

        return gameState.copy(
            fightHasEnded = checkFightEnd(gameState),
            currentTurn = gameState.currentTurn + 1,
            enemies = calculateEnemyTurn(gameState),
            magicToPlay = refreshMagicToPlay(gameState.magicToPlay)
        )
    }

    private fun checkFightEnd(gameState: MyGameState): FightOutcome {
        return when {
            gameState.enemies.none { it.health > 0 } -> WIN
            gameState.mages.none { it.health > 0 } -> LOST
            else -> ONGOING
        }
    }

    private fun refreshMagicToPlay(leftOverMagic: List<Magic>): List<Magic> {
        val randomData = random.nextDouble().toString()
        val newMagicToPlay =
            leftOverMagic + Magic(
                id = MagicId(HashUuidFunctions.v5(randomData)),
                type = if(Random.nextBoolean()) MagicType.GREEN else MagicType.SIMPLE,
            )
        check(newMagicToPlay.containsAll(leftOverMagic)) { "New magic to play does not contain all of the old" }
        return newMagicToPlay
    }

    private fun calculateEnemyTurn(gameState: MyGameState): List<Enemy> {
        return gameState.enemies.map { enemy ->
            val nextAction = enemy.possibleActions.random()
            val nextGameAction = nextAction.init(enemy.id, gameState)
            enemy.copy(nextAction = nextGameAction)
        }
    }
}
