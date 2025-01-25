package de.moyapro.colors.ui.view.components.previewdata

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import de.moyapro.colors.createExampleEnemy
import de.moyapro.colors.game.model.FieldId
import de.moyapro.colors.game.model.gameState.BattleBoard
import de.moyapro.colors.game.model.gameState.Field
import de.moyapro.colors.game.model.gameState.Terrain


class BattleBoardPreviewProviderBattleBoard(override val values: Sequence<BattleBoard> = sequenceOf(simpleMiddle(), largeEnemies())) : PreviewParameterProvider<BattleBoard>

fun simpleMiddle(): BattleBoard {
    return BattleBoard(
        fields = listOf(
            Field(FieldId(0), null, Terrain.PLAIN),
            Field(FieldId(1), null, Terrain.ROCK),
            Field(FieldId(2), null, Terrain.FORREST),
            Field(FieldId(3), null, Terrain.WATER),
            Field(FieldId(4), null, Terrain.SAND),
            Field(FieldId(5), createExampleEnemy(), Terrain.PLAIN),
            Field(FieldId(6), createExampleEnemy(), Terrain.ROCK),
            Field(FieldId(7), createExampleEnemy(), Terrain.FORREST),
            Field(FieldId(8), createExampleEnemy(), Terrain.WATER),
            Field(FieldId(9), createExampleEnemy(), Terrain.SAND),
            Field(FieldId(10), null, Terrain.PLAIN),
            Field(FieldId(11), null, Terrain.ROCK),
            Field(FieldId(12), null, Terrain.FORREST),
            Field(FieldId(13), null, Terrain.WATER),
            Field(FieldId(14), null, Terrain.SAND),
        )
    )
}

fun largeEnemies(): BattleBoard {
    return BattleBoard(
        fields = listOf(
            Field(FieldId(0), createExampleEnemy(breadth = 2, size = 2), Terrain.PLAIN),
            Field(FieldId(1), null, Terrain.ROCK),
            Field(FieldId(2), null, Terrain.FORREST),
            Field(FieldId(3), null, Terrain.WATER),
            Field(FieldId(4), null, Terrain.SAND),
            Field(FieldId(5), createExampleEnemy(), Terrain.PLAIN),
            Field(FieldId(6), null, Terrain.ROCK),
            Field(FieldId(7), createExampleEnemy(breadth = 3, size = 2), Terrain.FORREST),
            Field(FieldId(8), null, Terrain.WATER),
            Field(FieldId(9), null, Terrain.SAND),
            Field(FieldId(10), null, Terrain.PLAIN),
            Field(FieldId(11), null, Terrain.ROCK),
            Field(FieldId(12), null, Terrain.FORREST),
            Field(FieldId(13), null, Terrain.WATER),
            Field(FieldId(14), null, Terrain.SAND),
        )
    )
}