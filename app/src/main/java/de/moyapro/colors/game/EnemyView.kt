package de.moyapro.colors.game

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.enemy.*
import de.moyapro.colors.util.*

private const val targetSymbol = "\uD83C\uDFAF"

@Composable
fun EnemyView(enemy: Enemy, addAction: (GameAction) -> GameViewModel) {
    Box(
        Modifier
            .size(72.dp)
            .border(1.dp, Color.Black)
    ) {
        if (enemy.showTarget)
            Text(
                text = targetSymbol,
                color = Color.Red,
                fontSize = 48.sp,
                modifier = Modifier
                    .clickable(onClick = { addAction(TargetSelectedAction(enemy.id)) })
                    .alpha(DROP_ZONE_ALPHA)
            )
        Column {
            Text("Enemy")
            HealthView(enemy.health)
            Text(enemy.nextAction.name)
        }
    }
}
