package de.moyapro.colors.ui.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.moyapro.colors.game.functions.getTag
import de.moyapro.colors.game.model.Spell
import de.moyapro.colors.game.spell.Acid
import de.moyapro.colors.game.spell.Bonk
import de.moyapro.colors.game.spell.Fizz
import de.moyapro.colors.game.spell.Splash
import de.moyapro.colors.util.SPELL_SIZE

@Composable
fun SpellView(
    spell: Spell<*>?,
    modifier: Modifier = Modifier,
    clickAction: ((spell: Spell<*>) -> Unit)? = null,
) {
    val modifierWithClickAction = when {
        spell == null || clickAction == null -> modifier
        else -> modifier.clickable { clickAction(spell) }
    }
    Box(modifier = modifier.size(SPELL_SIZE.dp)) {
        if (spell != null) {
            Image(
                painter = painterResource(spell.image.imageRef), contentDescription = "Name", modifier = modifierWithClickAction
                    .size(SPELL_SIZE.dp)
                    .testTag(getTag(spell))
            )
            MagicSlotsView(spell.magicSlots)
        }
    }
}

@Composable
@Preview
fun SpellViewPreview() {
    Column {
        listOf(Acid(), Bonk(), Fizz(), Splash()).forEach {
            SpellView(it)
        }
    }
}

