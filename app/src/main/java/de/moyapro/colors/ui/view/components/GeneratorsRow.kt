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
import de.moyapro.colors.util.SPELL_SIZE


@Composable
fun GeneratorsRow(generators: List<MagicGenerator>) {
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