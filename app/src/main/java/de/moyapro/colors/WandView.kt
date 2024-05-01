package de.moyapro.colors

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.takeTwo.*
import de.moyapro.colors.util.*

@Composable
fun WandView(
    modifier: Modifier = Modifier,
    wand: Wand = createExampleWand(),
    addAction: (GameAction) -> GameViewModel,
    currentGameState: MyGameState,
) {
    val mage = currentGameState.findMage(wand.id)
    Column(
        modifier = modifier
            .height(4 * SPELL_SIZE.dp)
            .width(2 * SPELL_SIZE.dp)
    ) {
        if (mage != null) {
            Row(Modifier.fillMaxWidth()) {
                Button(
                    modifier = Modifier.width(SPELL_SIZE.dp),
                    onClick = { addAction(ZapAction(wand.id)) }) { Text("🗲") }
                MageView(
                    modifier = Modifier
                        .width(SPELL_SIZE.dp)
                        .height(SPELL_SIZE.dp),
                    mage = mage
                )
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
                    items(slotsByLevel[level]!!, key = { slot -> slot.hashCode() })
                    { slot -> SlotView(wand.id, slot, addAction, currentGameState) }
                }
            }
        }

    }
}
