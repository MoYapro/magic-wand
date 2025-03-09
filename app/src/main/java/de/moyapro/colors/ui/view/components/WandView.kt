package de.moyapro.colors.ui.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import de.moyapro.colors.createExampleWand
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.functions.getTag
import de.moyapro.colors.game.generators.Initializer
import de.moyapro.colors.game.model.Slot
import de.moyapro.colors.game.model.Wand
import de.moyapro.colors.game.model.gameState.GameState
import de.moyapro.colors.util.SPELL_SIZE

@Composable
fun WandView(
    modifier: Modifier = Modifier,
    wand: Wand,
    addAction: (GameAction) -> Unit,
    currentGameState: GameState,
    clickAction: (() -> Unit)? = null,
) {
    val actualModifier = if (clickAction != null) modifier.clickable(onClick = clickAction) else modifier
    val slotsByLevel = wand.slots.groupBy(Slot::level).toSortedMap { key1, key2 -> key2.compareTo(key1) }
    val maxLevel = slotsByLevel.keys.max()

    Column(
        modifier = actualModifier
            .height(5 * SPELL_SIZE.dp)
            .width(2 * SPELL_SIZE.dp)
            .testTag(getTag(wand))
    ) {
        (maxLevel downTo 0).forEach { level ->
            LazyRow(modifier = modifier.align(Alignment.CenterHorizontally)) {
                items(slotsByLevel[level]!!, key = { slot -> slot.hashCode() }) { slot -> SlotView(wand.id, slot, addAction, currentGameState) }
            }
        }
    }
}

@Preview
@Composable
fun WandViewPreview() {
    WandView(wand = createExampleWand(), addAction = {}, currentGameState = Initializer.createInitialGameState(), clickAction = {})
}
