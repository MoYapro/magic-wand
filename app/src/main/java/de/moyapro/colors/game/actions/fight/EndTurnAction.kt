package de.moyapro.colors.game.actions.fight

import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.enemy.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.gameState.*
import de.moyapro.colors.util.*
import de.moyapro.colors.util.FightOutcome.LOST
import de.moyapro.colors.util.FightOutcome.ONGOING
import de.moyapro.colors.util.FightOutcome.WIN
import kotlin.random.*


data class EndTurnAction(override val randomSeed: Int = 1) : GameAction("End turn") {

    private lateinit var random: Random

    override fun apply(oldState: NewGameState): Result<NewGameState> {
        random = Random(randomSeed)
        val foldingFunction = { state: Result<NewGameState>, action: GameAction ->
            state.flatMap { action.apply(it) }
        }
        val stateAfterEnemyActions =
            oldState.currentFight.battleBoard.getEnemies().map(Enemy::nextAction).fold(Result.success(oldState), foldingFunction)
        return stateAfterEnemyActions.map(this::prepareNextTurn)
    }


    private fun prepareNextTurn(gameState: NewGameState): NewGameState {

        return gameState.updateCurrentFight(
            currentTurn = gameState.currentFight.currentTurn + 1,
            fightHasEnded = checkFightEnd(gameState),
            battlefield = calculateEnemyTurn(gameState),
            mages = gameState.currentFight.mages,
            wands = gameState.currentFight.wands,
            magicToPlay = refreshMagicToPlay(gameState.currentFight.magicToPlay)
        )
    }

    private fun checkFightEnd(gameState: NewGameState): FightOutcome {
        return when {
            gameState.currentFight.battleBoard.getEnemies().none { it.health > 0 } -> WIN
            gameState.currentFight.mages.none { it.health > 0 } -> LOST
            else -> ONGOING
        }
    }

    private fun refreshMagicToPlay(leftOverMagic: List<Magic>): List<Magic> {
        val randomData = random.nextDouble().toString()
        val newMagicToPlay =
            leftOverMagic + Magic(
                id = MagicId(HashUuidFunctions.v5(randomData)),
                type = if (random.nextBoolean()) MagicType.GREEN else MagicType.SIMPLE,
            )
        check(newMagicToPlay.containsAll(leftOverMagic)) { "New magic to play does not contain all of the old" }
        return newMagicToPlay
    }

    private fun calculateEnemyTurn(gameState: NewGameState): BattleBoard {
        return gameState.currentFight.battleBoard.mapEnemies { enemy ->
            val nextAction = enemy.possibleActions.random()
            val nextGameAction = nextAction.init(enemy.id, gameState)
            enemy.copy(nextAction = nextGameAction)
        }
    }
}
