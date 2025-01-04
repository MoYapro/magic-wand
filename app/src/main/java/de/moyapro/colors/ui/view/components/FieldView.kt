package de.moyapro.colors.ui.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.actions.fight.TargetSelectedAction
import de.moyapro.colors.game.model.gameState.Field
import de.moyapro.colors.game.model.gameState.Terrain
import de.moyapro.colors.ui.view.fight.EnemyView
import de.moyapro.colors.util.DROP_ZONE_ALPHA
import de.moyapro.colors.util.ENEMY_SIZE

private const val targetSymbol = "\uD83C\uDFAF"

@Composable
fun FieldView(
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
            EnemyView(enemy = field.enemy)
        }
        if (field.showTarget) {

            Text(
                text = targetSymbol,
                color = Color.Red,
                fontSize = 48.sp,
                modifier = Modifier
                    .clickable(onClick = { addAction(TargetSelectedAction(field.id)) })
                    .alpha(DROP_ZONE_ALPHA)
            )
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