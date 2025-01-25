package de.moyapro.colors.ui.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
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
    val aggegatedGenerators: Map<MagicType, IntRange> = getAggegatedGenerators(generators)
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(1f / 2f)
            .border(1.dp, Color.LightGray)
    ) {
        items(
            items = aggegatedGenerators.entries.toList().sortedBy { it.key.ordinal },
            key = { generator: Map.Entry<MagicType, IntRange> -> generator.hashCode() }) { generator: Map.Entry<MagicType, IntRange> ->
            GeneratorView(
                generator
            )
        }
    }
}


fun getAggegatedGenerators(generators: List<MagicGenerator>) = generators.map { it.magicType to it.amountRange }.groupBy({ it.first }, { it.second }).mapValues { (_, ranges) ->
    ranges.fold(0..0) { acc, range ->
        (acc.first + range.first)..(acc.last + range.last)
    }
}

@Composable
fun GeneratorView(generator: Map.Entry<MagicType, IntRange>) {
    val modifier = Modifier
        .height(SPELL_SIZE.dp)
        .width(SPELL_SIZE.dp)
    Box(modifier) {
        Text(generator.value.last.toString() + "\n" + generator.value.first.toString())
        Image(
            modifier = modifier,
            painter = painterResource(generator.key.image.imageRef),
            contentDescription = "Name",
        )
    }
}