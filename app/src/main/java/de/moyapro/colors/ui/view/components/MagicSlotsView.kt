package de.moyapro.colors.ui.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.moyapro.colors.game.model.MagicSlot
import de.moyapro.colors.util.MAGIC_SIZE

@Composable
fun MagicSlotsView(magicSlots: List<MagicSlot>) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        LazyColumn(modifier = Modifier.height((magicSlots.size * MAGIC_SIZE).dp)) {
            items(items = magicSlots, key = { magicSlot: MagicSlot -> magicSlot.hashCode() }) { magicSlot: MagicSlot ->
                MagicSlotView(
                    modifier = Modifier
                        .width(MAGIC_SIZE.dp)
                        .height(MAGIC_SIZE.dp), magicSlot
                )
            }
        }
    }
}
