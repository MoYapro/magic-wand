package de.moyapro.colors.ui.view.fight

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.enemy.*
import de.moyapro.colors.util.*


@Composable
fun EnemyView(enemy: Enemy, addAction: (GameAction) -> Unit) {
    Box(
        Modifier
            .width((enemy.breadth) * ENEMY_SIZE.dp)
            .height((enemy.size) * ENEMY_SIZE.dp)
            .border(1.dp, Color.Black)
    ) {
        Column {
            Text("Enemy")
            HealthView(enemy.health)
            Text(enemy.nextAction.name)
        }
    }
}
