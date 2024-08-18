package de.moyapro.colors.ui.view.components.previewdata

import androidx.compose.ui.tooling.preview.*
import de.moyapro.colors.*
import de.moyapro.colors.game.model.gameState.*


class BattleBoardPreviewProviderBattleBoard(override val values: Sequence<BattleBoard> = sequenceOf(initPreviewBattleBoard())) : PreviewParameterProvider<BattleBoard>

fun initPreviewBattleBoard(): BattleBoard {
    return BattleBoard(
        fields = listOf(
            Field(null, Terrain.PLAIN),
            Field(null, Terrain.ROCK),
            Field(null, Terrain.TREE),
            Field(null, Terrain.WATER),
            Field(null, Terrain.SAND),
            Field(createExampleEnemy(), Terrain.PLAIN),
            Field(createExampleEnemy(), Terrain.ROCK),
            Field(createExampleEnemy(), Terrain.TREE),
            Field(createExampleEnemy(), Terrain.WATER),
            Field(createExampleEnemy(), Terrain.SAND),
            Field(null, Terrain.PLAIN),
            Field(null, Terrain.ROCK),
            Field(null, Terrain.TREE),
            Field(null, Terrain.WATER),
            Field(null, Terrain.SAND),
        )
    )
}