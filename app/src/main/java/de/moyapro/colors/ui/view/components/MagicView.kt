package de.moyapro.colors.ui.view.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import de.moyapro.colors.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.ui.view.dragdrop.*
import de.moyapro.colors.util.*

@Preview
@Composable
fun MagicView(magic: Magic = createExampleMagic()) {
    Draggable(
        dataToDrop = magic
    ) { theMagic, _ ->
        Canvas(modifier = Modifier.size(SPELL_SIZE.dp), onDraw = {
            drawCircle(color = theMagic.type.color)
        })
    }
}
