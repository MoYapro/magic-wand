package de.moyapro.colors.ui.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import de.moyapro.colors.game.model.MagicGenerator
import de.moyapro.colors.game.model.MagicType
import de.moyapro.colors.util.SPELL_SIZE


@Composable
fun GeneratorsRow(generators: List<MagicGenerator>) {
    val aggegatedGenerators: Map<MagicType, IntRange> = generators
        .map { it.magicType to it.amountRange }
        .groupBy({ it.first }, { it.second })
        .mapValues { (_, ranges) ->
            ranges.fold(IntRange(Int.MAX_VALUE, Int.MIN_VALUE)) { acc, range ->
                IntRange(minOf(acc.first, range.first), maxOf(acc.last, range.last))
            }
        }
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(1f / 2f)
            .border(1.dp, Color.LightGray)
    ) {
        items(items = generators, key = { generator: MagicGenerator -> generator.hashCode() }) { generator: MagicGenerator -> GeneratorView(generator) }
    }
}

@Composable
fun GeneratorView(generator: MagicGenerator) {
    Image(
        modifier = Modifier
            .height(SPELL_SIZE.dp)
            .width(SPELL_SIZE.dp),
        painter = painterResource(generator.magicType.image.imageRef),
        contentDescription = "Name",
    )
}