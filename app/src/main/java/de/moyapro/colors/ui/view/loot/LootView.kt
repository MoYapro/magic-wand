package de.moyapro.colors.ui.view.loot

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import de.moyapro.colors.game.model.Spell
import de.moyapro.colors.ui.view.components.SpellView
import de.moyapro.colors.util.SPELL_SIZE

@Composable
fun LootView(newSpells: List<Spell<*>>) {

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth()
            .height(2 * SPELL_SIZE.dp),
        columns = GridCells.FixedSize(SPELL_SIZE.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalArrangement = Arrangement.SpaceEvenly,
        userScrollEnabled = false,
    ) {
        items(
            items = newSpells,
            key = { spell -> spell.id.hashCode() }) { spell ->
            SpellView(spell = spell)
        }
    }
}