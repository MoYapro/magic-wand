package de.moyapro.colors.ui.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import de.moyapro.colors.game.functions.getTag
import de.moyapro.colors.game.model.Spell
import de.moyapro.colors.util.SPELL_SIZE
import de.moyapro.colors.R

@Composable
fun SpellView(
    spell: Spell<*>?,
    modifier: Modifier = Modifier,
    clickAction: ((spell: Spell<*>) -> Unit)? = null,
) {
    if (spell == null) return
    val actualModifier = if (clickAction == null) modifier else modifier.clickable { clickAction(spell) }
    val image = painterResource(spell.image?.imageRef ?: R.drawable.bad_heart)
    Image(
        painter = image, contentDescription = "Name", modifier = actualModifier
            .height(SPELL_SIZE.dp)
            .width(SPELL_SIZE.dp)
            .testTag(getTag(spell))

    )
    MagicSlotsView(spell.magicSlots)
}

