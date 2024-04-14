package de.moyapro.colors

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.takeTwo.*
import de.moyapro.colors.util.*


@Composable
fun LootWandsView(
    wands: List<Wand>,
    currentGameState: MyGameState,
    addAction: (GameAction) -> GameViewModel,
) {
    if (wands.size >= 1) {
        LazyRow(
            modifier = Modifier
                .fillMaxSize()
                .border(1.dp, Color.LightGray)
        ) {
            items(
                items = wands,
                key = { wand -> wand.id.hashCode() }) { wand ->
                Draggable(dataToDrop = wand, requireLongPress = true) {
                    WandEditView(
                        modifier = Modifier.width(2 * SPELL_SIZE.dp),
                        wand = wand,
                        addAction = addAction,
                        currentGameState = currentGameState
                    )
                }
            }
        }


    } else {
        Text("Win fights to get wands")
    }
}
