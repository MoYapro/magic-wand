package de.moyapro.colors.ui.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import de.moyapro.colors.game.model.MagicSlot
import de.moyapro.colors.game.model.Spell
import de.moyapro.colors.util.MAGIC_SIZE
import de.moyapro.colors.util.SPELL_SIZE
import de.moyapro.colors.R

@Composable
fun SpellView(spell: Spell<*>?) {
    val image = painterResource(R.drawable.bad_heart)
    Image(
        painter = image, contentDescription = "Name", modifier = Modifier
            .height(SPELL_SIZE.dp)
            .width(SPELL_SIZE.dp)
    )
    if (spell != null) MagicSlotsView(spell.magicSlots)
}

@Composable
private fun MagicSlotsView(magicSlots: List<MagicSlot>) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        LazyColumn {
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
