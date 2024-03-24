package de.moyapro.colors

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.moyapro.colors.wand.Spell

@Composable
fun SpellView(modifier: Modifier = Modifier, spell: Spell) {
    Text(modifier = modifier, text = spell.name)
}