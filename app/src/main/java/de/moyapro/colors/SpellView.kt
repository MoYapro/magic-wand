package de.moyapro.colors

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.unit.*
import de.moyapro.colors.util.*
import de.moyapro.colors.wand.*

@Composable
fun SpellView(modifier: Modifier = Modifier, spell: Spell?) {
    val image = painterResource(R.drawable.bad_heart)
    Image(
        painter = image,
        contentDescription = "Name",
        modifier = Modifier.height(SPELL_SIZE.dp).width(SPELL_SIZE.dp)
    )
    Text(modifier = modifier, text = "I" + spell?.name ?: "No Spell")
}
