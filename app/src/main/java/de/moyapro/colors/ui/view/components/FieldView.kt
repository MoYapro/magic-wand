package de.moyapro.colors.ui.view.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.actions.fight.*
import de.moyapro.colors.game.model.gameState.*
import de.moyapro.colors.ui.view.fight.*
import de.moyapro.colors.util.*

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
            EnemyView(enemy = field.enemy, addAction)
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