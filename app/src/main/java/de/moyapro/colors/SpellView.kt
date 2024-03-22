package de.moyapro.colors

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import de.moyapro.colors.wand.Spell

@Composable
fun SpellView(spell: Spell) {
    Text(text = spell.name)
}