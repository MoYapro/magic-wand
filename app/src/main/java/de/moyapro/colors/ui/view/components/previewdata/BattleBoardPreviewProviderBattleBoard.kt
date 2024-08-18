package de.moyapro.colors.ui.view.components.previewdata

import androidx.compose.ui.tooling.preview.*
import de.moyapro.colors.*
import de.moyapro.colors.game.model.gameState.*


class BattleBoardPreviewProviderBattleBoard(override val values: Sequence<BattleBoard> = sequenceOf(simpleMiddle(), largeEnemies())) : PreviewParameterProvider<BattleBoard>

fun simpleMiddle(): BattleBoard {
    return BattleBoard(
        fields = listOf(
            Field(null, Terrain.PLAIN),
            Field(null, Terrain.ROCK),
            Field(null, Terrain.FORREST),
            Field(null, Terrain.WATER),
            Field(null, Terrain.SAND),
            Field(createExampleEnemy(), Terrain.PLAIN),
            Field(createExampleEnemy(), Terrain.ROCK),
            Field(createExampleEnemy(), Terrain.FORREST),
            Field(createExampleEnemy(), Terrain.WATER),
            Field(createExampleEnemy(), Terrain.SAND),
            Field(null, Terrain.PLAIN),
            Field(null, Terrain.ROCK),
            Field(null, Terrain.FORREST),
            Field(null, Terrain.WATER),
            Field(null, Terrain.SAND),
        )
    )
}

fun largeEnemies(): BattleBoard {
    return BattleBoard(
        fields = listOf(
            Field(createExampleEnemy(breadth = 1, size = 1), Terrain.PLAIN),
            Field(null, Terrain.ROCK),
            Field(null, Terrain.FORREST),
            Field(null, Terrain.WATER),
            Field(null, Terrain.SAND),

            Field(createExampleEnemy(), Terrain.PLAIN),
            Field(null, Terrain.ROCK),
            Field(createExampleEnemy(breadth = 1, size = 1), Terrain.FORREST),
            Field(null, Terrain.WATER),
            Field(null, Terrain.SAND),

            Field(null, Terrain.PLAIN),
            Field(null, Terrain.ROCK),
            Field(null, Terrain.FORREST),
            Field(null, Terrain.WATER),
            Field(null, Terrain.SAND),
        )
    )
}