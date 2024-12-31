package de.moyapro.colors.ui.view.fight

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import de.moyapro.colors.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.effect.*
import de.moyapro.colors.game.enemy.*
import de.moyapro.colors.util.*
import kotlin.math.*


@Composable
@Preview
fun EnemyView(@PreviewParameter(EnemyPreviewProvider::class) enemy: Enemy) {
    Box(
        Modifier
            .width((enemy.breadth) * ENEMY_SIZE.dp)
            .height((enemy.size) * ENEMY_SIZE.dp)
            .border(1.dp, Color.Black)
    ) {
        val image = painterResource(listOf(R.drawable.grunt_l, R.drawable.grunt_c, R.drawable.grunt_r, R.drawable.slime_l, R.drawable.slime_l, R.drawable.slime_c).random())
        Image(
            painter = image, contentDescription = "Name", modifier = Modifier
                .height(SPELL_SIZE.dp)
                .width(SPELL_SIZE.dp)
        )
        Column(Modifier.size(SPELL_SIZE.dp), verticalArrangement = Arrangement.SpaceBetween) {
            StatusEffectsView(enemy.statusEffects)
            Text(enemy.nextAction.name)
            HealthView(enemy.health)
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
            if (amount > 0)
                Icon(
                    imageVector = effect.icon,
                    contentDescription = "Localized description",
                    tint = effect.color.copy(alphaForAmount(amount)),
                    modifier = Modifier.size(16.dp) // Change the size
                )
        }
    }

}

fun alphaForAmount(input: Int): Float {
    return (1 - 0.7 * exp(-0.1 * input)).toFloat()
}

class EnemyPreviewProvider(
    override val values: Sequence<Enemy> = sequenceOf(
        Enemy(health = 10),
        Enemy(health = 101, nextAction = UndoAction),
        Enemy(health = 10, statusEffects = Effect.values().mapIndexed { index, effect -> effect to ((index + 1) * 5) }.associate { it }),
        Enemy(health = 10, statusEffects = Effect.values().reversed().mapIndexed { index, effect -> effect to ((index + 1) * 5) }.associate { it }),
    ),
) : PreviewParameterProvider<Enemy>


