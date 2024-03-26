package de.moyapro.colors.game

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.actions.TargetSelectedAction

private const val targetSymbol = "\uD83C\uDFAF"

@Composable
fun EnemyView(enemy: Enemy, addAction: (GameAction) -> GameViewModel) {
    Box(
        Modifier
            .size(72.dp)
            .border(1.dp, Color.Black)
    ) {
        if (enemy.showTarget)
            Button(onClick = { addAction(TargetSelectedAction(enemy.id)) }) {
                Text(text = targetSymbol, color = Color.Red, fontSize = 48.sp)
            }
        Column {
            Text("Enemy")
            HealthView(enemy.health)
            Text(enemy.nextAction.name)
        }
    }
}
