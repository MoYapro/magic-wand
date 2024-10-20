package de.moyapro.colors.game.actions.fight

import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.enemy.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.gameState.*
import de.moyapro.colors.util.*
import de.moyapro.colors.util.FightState.LOST
import de.moyapro.colors.util.FightState.ONGOING
import de.moyapro.colors.util.FightState.WIN
import kotlin.random.*


data class EndTurnAction(override val randomSeed: Int = 1) : GameAction("End turn") {

    private lateinit var random: Random

    override fun apply(oldState: GameState): Result<GameState> {
        random = Random(randomSeed)
        val stateAfterEnemyActions =
            oldState.currentFight.battleBoard.getEnemies().map(Enemy::nextAction).fold(Result.success(oldState), ::applyAllActions)
        return stateAfterEnemyActions.map(this::prepareNextTurn)
    }


    private fun prepareNextTurn(gameState: GameState): GameState {

        return gameState.updateCurrentFight(
            currentTurn = nextTurn(gameState.currentFight.currentTurn),
            fightState = checkFightEnd(gameState.currentFight),
            battlefield = calculateEnemyTurn(gameState),
            magicToPlay = refreshMagicToPlay(gameState.currentFight.magicToPlay, gameState.currentFight.generators)
        )
    }

    private fun checkFightEnd(fight: FightData): FightState {
        return when {
            fight.battleBoard.getEnemies().none { it.health > 0 } -> WIN
            fight.mages.none { it.health > 0 } -> LOST
            fight.fightState == ONGOING -> ONGOING
            else -> throw IllegalStateException("Cannot progress fight state from: ${fight.fightState}")
        }
    }

    private fun nextTurn(currentTurn: Int): Int {
        return currentTurn + 1
    }

    private fun refreshMagicToPlay(leftOverMagic: List<Magic>, generators: List<MagicGenerator>): List<Magic> {
        val generatedMagic = generators.flatMap(MagicGenerator::generate)
        val newMagicToPlay = leftOverMagic + generatedMagic
        check(newMagicToPlay.containsAll(leftOverMagic)) { "New magic to play does not contain all of the old" }
        return newMagicToPlay
    }

    private fun calculateEnemyTurn(gameState: GameState): BattleBoard {
        return gameState.currentFight.battleBoard.mapEnemies { enemy ->
            val nextAction = enemy.possibleActions.random()
            val nextGameAction = nextAction.init(enemy.id, gameState)
            enemy.copy(nextAction = nextGameAction)
        }
    }
}
