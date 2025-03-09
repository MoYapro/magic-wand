package de.moyapro.colors.ui.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import de.moyapro.colors.createExampleWand
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.actions.fight.ZapAction
import de.moyapro.colors.game.functions.getTag
import de.moyapro.colors.game.model.Slot
import de.moyapro.colors.game.model.Wand
import de.moyapro.colors.game.model.accessor.findMage
import de.moyapro.colors.game.model.gameState.GameState
import de.moyapro.colors.util.SPELL_SIZE

@Composable
fun WandView(
    modifier: Modifier = Modifier,
    wand: Wand = createExampleWand(),
    addAction: (GameAction) -> Unit,
    currentGameState: GameState,
    clickAction: (() -> Unit)? = null,
) {
    val mage = currentGameState.currentFight.mages.findMage(wand.id)
    val actualModifier = if (clickAction != null) modifier.clickable(onClick = clickAction) else modifier
    Column(
        modifier = actualModifier
            .height(4 * SPELL_SIZE.dp)
            .width(2 * SPELL_SIZE.dp)
            .testTag(getTag(wand))
    ) {
        if (mage != null) {
            Row(Modifier.fillMaxWidth()) {
                Button(
                    enabled = wand.hasAnySpellToZap() && mage.health > 0,
                    modifier = Modifier.width(SPELL_SIZE.dp),
                    onClick = { addAction(ZapAction(wand.id)) }
                ) { Text(text = "Zap") }
                MageView(mage = mage)
            }
        }
        val slotsByLevel = wand.slots.groupBy(Slot::level).toSortedMap { key1, key2 -> key2.compareTo(key1) }
        val maxLevel = slotsByLevel.keys.max()

        Column(
            modifier = modifier
                .height(5 * SPELL_SIZE.dp)
                .width(2 * SPELL_SIZE.dp)
        ) {
            (maxLevel downTo 0).forEach { level ->
                LazyRow(modifier = modifier.align(Alignment.CenterHorizontally)) {
                    items(slotsByLevel[level]!!, key = { slot -> slot.hashCode() }) { slot -> SlotView(wand.id, slot, addAction, currentGameState) }
                }
            }
        }
    }
}
