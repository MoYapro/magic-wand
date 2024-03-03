package de.moyapro.colors.game

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun EnemyView(enemy: Enemy) {
    Box(Modifier.size(72.dp).border(1.dp, Color.Black)) {
        Column {
            Text("Enemy")
            Row {
                repeat(enemy.health) {
                    HealthView()
                }
            }
        }
    }
}

@Composable
fun HealthView() {
    Text(
        text = "O",
        color = Color.Red
    )
}
