package de.moyapro.colors.ui.view.fight

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.effect.*
import de.moyapro.colors.game.enemy.*
import de.moyapro.colors.util.*


@Composable
@Preview
fun EnemyView(@PreviewParameter(EnemyPreviewProvider::class) enemy: Enemy) {
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
            StatusEffectsView(enemy.statusEffects)
        }
    }
}

@Composable
fun StatusEffectsView(statusEffects: Map<Effect, Int>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(16.dp)
    ) {
        items(
            items = statusEffects.entries.toList(),
            key = { (effect, _) -> effect }
        ) { (effect: Effect, amount: Int) ->

            Icon(
                imageVector = effect.icon,
                contentDescription = "Localized description",
                tint = effect.color,
                modifier = Modifier.size(16.dp) // Change the size
            )
        }
    }

}

class EnemyPreviewProvider(
    override val values: Sequence<Enemy> = sequenceOf(
        Enemy(health = 10),
        Enemy(health = 101, nextAction = UndoAction),
        Enemy(health = 10, statusEffects = Effect.values().map { it to 1 }.associate { it })
    ),
) : PreviewParameterProvider<Enemy>


