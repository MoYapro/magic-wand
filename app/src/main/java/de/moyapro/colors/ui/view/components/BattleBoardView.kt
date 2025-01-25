package de.moyapro.colors.ui.view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.enemy.Enemy
import de.moyapro.colors.game.model.gameState.BattleBoard
import de.moyapro.colors.ui.view.components.previewdata.BattleBoardPreviewProviderBattleBoard
import de.moyapro.colors.util.BATTLE_FIELD_WIDTH
import de.moyapro.colors.util.ENEMY_SIZE
import java.util.LinkedList


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
    addAction: (GameAction) -> Unit = { },
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
            if (displedFields[index]) FieldView(modifier.offset(offsetY, offsetX), field, addAction)
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



