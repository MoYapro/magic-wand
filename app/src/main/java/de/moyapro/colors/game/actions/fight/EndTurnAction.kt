package de.moyapro.colors.game.actions.fight

import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.actions.applyAllActions
import de.moyapro.colors.game.effect.applyFire
import de.moyapro.colors.game.effect.applyPoison
import de.moyapro.colors.game.effect.applyWet
import de.moyapro.colors.game.enemy.Enemy
import de.moyapro.colors.game.model.Magic
import de.moyapro.colors.game.model.MagicGenerator
import de.moyapro.colors.game.model.gameState.BattleBoard
import de.moyapro.colors.game.model.gameState.FightData
import de.moyapro.colors.game.model.gameState.GameState
import de.moyapro.colors.util.FightState
import de.moyapro.colors.util.FightState.*
import kotlin.random.Random


data class EndTurnAction(override val randomSeed: Int = 1) : GameAction("End turn") {

    private lateinit var random: Random

    override fun apply(oldState: GameState): Result<GameState> {
        random = Random(randomSeed)
        return executeAllEnemyActions(oldState)
            .map(::prepareNextTurn)
            .map(::doEffects)

    }

    private fun executeAllEnemyActions(oldState: GameState) =
        oldState.currentFight.battleBoard.getEnemies().map(Enemy::nextAction).fold(Result.success(oldState), ::applyAllActions)

    private fun doEffects(oldState: GameState): GameState {
        return oldState.updateCurrentFight(
            battleBoard = oldState
                .currentFight.battleBoard
                .mapEnemies(::applyPoison)
                .mapEnemies(::applyWet)
                .mapEnemies(::applyFire)
        )
    }


    private fun prepareNextTurn(gameState: GameState): GameState {

        return gameState.updateCurrentFight(
            currentTurn = nextTurn(gameState.currentFight.currentTurn),
            fightState = checkFightEnd(gameState.currentFight),
            battleBoard = calculateEnemyTurn(gameState),
            magicToPlay = refreshMagicToPlay(gameState.currentFight.magicToPlay, gameState.currentFight.generators, gameState.currentFight.currentTurn)
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

    private fun refreshMagicToPlay(leftOverMagic: List<Magic>, generators: List<MagicGenerator>, currentTurn: Int): List<Magic> {
        val generatedMagic = generators.flatMap { it.generate(currentTurn) }
        val newMagicToPlay = leftOverMagic + generatedMagic
        check(newMagicToPlay.containsAll(leftOverMagic)) { "New magic to play does contain all of the old" }
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
