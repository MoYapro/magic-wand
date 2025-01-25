package de.moyapro.colors.ui.view.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.moyapro.colors.game.model.Magic

@Composable
fun MagicRow(magicToPlay: List<Magic>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(1f / 2f)
            .border(1.dp, Color.LightGray)
    ) {
        items(items = magicToPlay.sortedBy { it.id.hashCode() }, key = { magic: Magic -> magic.hashCode() }) { magic: Magic -> MagicView(magic) }
    }

}
