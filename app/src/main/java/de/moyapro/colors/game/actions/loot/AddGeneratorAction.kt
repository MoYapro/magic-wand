package de.moyapro.colors.game.actions.loot

import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.model.MagicGenerator
import de.moyapro.colors.game.model.MagicType
import de.moyapro.colors.game.model.gameState.GameState
import kotlin.random.Random

data class AddGeneratorAction(
    val magicType: MagicType = MagicType.SIMPLE,
    override val randomSeed: Int = Random.nextInt(),
) : GameAction("Add Generator") {

    override fun apply(oldState: GameState): Result<GameState> {
        val newGenerator = MagicGenerator(magicType, 1..1)
        val updatedState = oldState.updateCurrentRun(generators = oldState.currentRun.generators + newGenerator)
        return Result.success(updatedState)
    }

}