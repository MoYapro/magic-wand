package de.moyapro.colors.ui.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import de.moyapro.colors.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.actions.fight.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.accessor.*
import de.moyapro.colors.game.model.gameState.*
import de.moyapro.colors.util.*

@Composable
fun WandView(
    modifier: Modifier = Modifier,
    wand: Wand = createExampleWand(),
    addAction: (GameAction) -> Unit,
    currentGameState: NewGameState,
) {
    val mage = currentGameState.currentFight.mages.findMage(wand.id)
    Column(
        modifier = modifier
            .height(4 * SPELL_SIZE.dp)
            .width(2 * SPELL_SIZE.dp)
    ) {
        if (mage != null) {
            Row(Modifier.fillMaxWidth()) {
                Button(
                    modifier = Modifier.width(SPELL_SIZE.dp),
                    onClick = { addAction(ZapAction(wand.id)) }) { Text(text = "Zap") }
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
