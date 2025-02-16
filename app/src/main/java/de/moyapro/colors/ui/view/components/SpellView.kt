package de.moyapro.colors.ui.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import de.moyapro.colors.game.model.Spell
import de.moyapro.colors.util.SPELL_SIZE
import de.moyapro.colors.R

@Composable
fun SpellView(spell: Spell<*>?) {
    val image = painterResource(R.drawable.bad_heart)
    Image(
        painter = image,
        contentDescription = "Name",
        modifier = Modifier
            .height(SPELL_SIZE.dp)
            .width(SPELL_SIZE.dp)
            .testTag(buildSpellTag(spell))
    )
    if (spell != null) MagicSlotsView(spell.magicSlots)
}

private fun buildSpellTag(spell: Spell<*>?): String {
    return "${spell?.id?.id}_${spell?.name}"
}
