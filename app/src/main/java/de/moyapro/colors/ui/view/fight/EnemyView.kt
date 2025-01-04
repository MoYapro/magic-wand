package de.moyapro.colors.ui.view.fight

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import de.moyapro.colors.game.actions.UndoAction
import de.moyapro.colors.game.effect.Effect
import de.moyapro.colors.game.enemy.Enemy
import de.moyapro.colors.game.enemy.blueprints.Grunt
import de.moyapro.colors.game.model.ImageRef
import de.moyapro.colors.util.ENEMY_SIZE
import de.moyapro.colors.R
import kotlin.math.exp


@Composable
@Preview
fun EnemyView(@PreviewParameter(EnemyPreviewProvider::class) enemy: Enemy) {
    Box(
        Modifier
            .width((enemy.breadth) * ENEMY_SIZE.dp)
            .height((enemy.size) * ENEMY_SIZE.dp)
            .border(1.dp, Color.Black)
    ) {
        val image: Painter = painterResource(selectImage(enemy).imageRef)
        Image(
            painter = image, contentDescription = "Name", modifier = Modifier
                .height(ENEMY_SIZE.dp)
                .width(ENEMY_SIZE.dp)
        )
        Column(Modifier.size(ENEMY_SIZE.dp), verticalArrangement = Arrangement.SpaceBetween) {
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

fun selectImage(enemy: Enemy): ImageRef {
    return enemy.image?.center ?: ImageRef(R.drawable.monster)
}

class EnemyPreviewProvider(
    override val values: Sequence<Enemy> = sequenceOf(
        Grunt(),
        Enemy(health = 101, nextAction = UndoAction),
        Enemy(health = 10, statusEffects = Effect.values().mapIndexed { index, effect -> effect to ((index + 1) * 5) }.associate { it }),
        Enemy(health = 10, statusEffects = Effect.values().reversed().mapIndexed { index, effect -> effect to ((index + 1) * 5) }.associate { it }),
    ),
) : PreviewParameterProvider<Enemy>


