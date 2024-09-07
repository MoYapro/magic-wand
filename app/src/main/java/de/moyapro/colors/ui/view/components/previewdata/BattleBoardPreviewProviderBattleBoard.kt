package de.moyapro.colors.ui.view.components.previewdata

import androidx.compose.ui.tooling.preview.*
import de.moyapro.colors.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.gameState.*


class BattleBoardPreviewProviderBattleBoard(override val values: Sequence<BattleBoard> = sequenceOf(simpleMiddle(), largeEnemies())) : PreviewParameterProvider<BattleBoard>

fun simpleMiddle(): BattleBoard {
    return BattleBoard(
        fields = listOf(
            Field(FieldId(1), null, Terrain.PLAIN),
            Field(FieldId(2), null, Terrain.ROCK),
            Field(FieldId(3), null, Terrain.FORREST),
            Field(FieldId(4), null, Terrain.WATER),
            Field(FieldId(5), null, Terrain.SAND),
            Field(FieldId(6), createExampleEnemy(), Terrain.PLAIN),
            Field(FieldId(7), createExampleEnemy(), Terrain.ROCK),
            Field(FieldId(8), createExampleEnemy(), Terrain.FORREST),
            Field(FieldId(9), createExampleEnemy(), Terrain.WATER),
            Field(FieldId(10), createExampleEnemy(), Terrain.SAND),
            Field(FieldId(11), null, Terrain.PLAIN),
            Field(FieldId(12), null, Terrain.ROCK),
            Field(FieldId(13), null, Terrain.FORREST),
            Field(FieldId(14), null, Terrain.WATER),
            Field(FieldId(15), null, Terrain.SAND),
        )
    )
}

fun largeEnemies(): BattleBoard {
    return BattleBoard(
        fields = listOf(
            Field(FieldId(1), createExampleEnemy(breadth = 2, size = 2), Terrain.PLAIN),
            Field(FieldId(2), null, Terrain.ROCK),
            Field(FieldId(3), null, Terrain.FORREST),
            Field(FieldId(4), null, Terrain.WATER),
            Field(FieldId(5), null, Terrain.SAND),

            Field(FieldId(6), createExampleEnemy(), Terrain.PLAIN),
            Field(FieldId(7), null, Terrain.ROCK),
            Field(FieldId(8), createExampleEnemy(breadth = 3, size = 2), Terrain.FORREST),
            Field(FieldId(9), null, Terrain.WATER),
            Field(FieldId(10), null, Terrain.SAND),

            Field(FieldId(11), null, Terrain.PLAIN),
            Field(FieldId(12), null, Terrain.ROCK),
            Field(FieldId(13), null, Terrain.FORREST),
            Field(FieldId(14), null, Terrain.WATER),
            Field(FieldId(15), null, Terrain.SAND),
        )
    )
}