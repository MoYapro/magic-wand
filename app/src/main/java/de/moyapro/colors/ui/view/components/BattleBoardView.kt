package de.moyapro.colors.ui.view.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.model.gameState.*
import de.moyapro.colors.ui.view.components.previewdata.*
import de.moyapro.colors.ui.view.fight.*
import de.moyapro.colors.util.*


/* battle board has fixed size (WxH: 5x3)
0 1 2 3 4
5 6 7 8 9
10 11 12 13 14

numbers in string representation point to same entity on all fields containing the same numbers
enemies can span multiple rows and colums
- means empty fields
0 0 - 2 2
0 0 - 3 3
4 5 6 7 8
 */
@Composable
@Preview
fun BattleBoardView(
    @PreviewParameter(provider = BattleBoardPreviewProviderBattleBoard::class)
    battleBoard: BattleBoard,
    modifier: Modifier = Modifier,
    addAction: (GameAction) -> GameViewModel = { GameViewModel() },
) {
    val displedFields = MutableList(15) { false }

    Column(modifier = modifier) {
        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            (0..4)
                .map { index -> battleBoard.fields[index] }
                .forEach { field ->
                    FieldView(field, modifier, addAction)
                }
        }
        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            (5..9)
                .map { index -> battleBoard.fields[index] }
                .forEach { field ->
                    FieldView(field, modifier, addAction)
                }
        }
        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            (10..14)
                .map { index -> battleBoard.fields[index] }
                .forEach { field ->
                    FieldView(field, modifier, addAction)
                }
        }
    }
}

@Composable
private fun FieldView(
    field: Field,
    modifier: Modifier,
    addAction: (GameAction) -> GameViewModel,
) {
    Box(
        modifier
            .height((field.enemy?.size ?: 1) * ENEMY_SIZE.dp)
            .width((field.enemy?.breadth ?: 1) * ENEMY_SIZE.dp)
            .border(1.dp, Color.LightGray)
            .background(color = getColorForTerrain(field.terrain))
    ) {
        if (field.enemy != null) {
            EnemyView(enemy = field.enemy, addAction)
        }
    }
}

fun getColorForTerrain(terrain: Terrain): Color {
    return when (terrain) {
        Terrain.PLAIN -> Color.Green
        Terrain.ROCK -> Color.Gray
        Terrain.WATER -> Color.Blue
        Terrain.SAND -> Color.Yellow
        Terrain.FORREST -> Color(165, 62, 2, alpha = 255)
    }
}

