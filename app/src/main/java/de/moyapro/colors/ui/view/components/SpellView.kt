package de.moyapro.colors.ui.view.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.unit.*
import de.moyapro.colors.R
import de.moyapro.colors.game.model.*
import de.moyapro.colors.util.*

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
