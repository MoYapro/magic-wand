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
import de.moyapro.colors.game.enemy.*
import de.moyapro.colors.game.model.gameState.*
import de.moyapro.colors.ui.view.components.previewdata.*
import de.moyapro.colors.ui.view.fight.*
import de.moyapro.colors.util.*
import java.util.*


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
    addAction: (GameAction) -> Unit = { GameViewModel() },
) {
    val displedFields = MutableList(15) { true }
    Box(
        modifier = modifier
            .width(5 * ENEMY_SIZE.dp)
            .height(3 * ENEMY_SIZE.dp)
    ) {
        require(battleBoard.fields.size == 15) { "Board must have 15 fields but was ${battleBoard.fields.size}" }
        (0..14).forEach { index ->
            val field = battleBoard.fields[index]
            val offsetX = ((index / 5) % 3) * ENEMY_SIZE.dp
            val offsetY = (index % 5) * ENEMY_SIZE.dp
            alreadyRenderedIndices(index, field.enemy).forEach {
                if (it != index) displedFields[it] = false
            }
            if (displedFields[index]) FieldView(field, modifier.offset(offsetY, offsetX), addAction)
        }
    }
}

fun alreadyRenderedIndices(index: Int, enemy: Enemy?): List<Int> {
    if (null == enemy) {
        return emptyList()
    }
    val fieldsBlockedByEnemy = LinkedList<Int>()
    repeat(enemy.breadth) { breadthIndex ->
        fieldsBlockedByEnemy.add(index + breadthIndex)
        repeat(enemy.size) { sizeIndex ->
            fieldsBlockedByEnemy.add(index + sizeIndex * BATTLE_FIELD_WIDTH)
            fieldsBlockedByEnemy.add(index + breadthIndex + sizeIndex * BATTLE_FIELD_WIDTH)
        }
    }
    return fieldsBlockedByEnemy
}

@Composable
private fun FieldView(
    field: Field,
    modifier: Modifier,
    addAction: (GameAction) -> Unit,
) {
    Box(
        modifier
            .height((field.enemy?.size ?: 1) * ENEMY_SIZE.dp)
            .width((field.enemy?.breadth ?: 1) * ENEMY_SIZE.dp)
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

