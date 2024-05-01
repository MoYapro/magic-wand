package de.moyapro.colors

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import de.moyapro.colors.util.*
import de.moyapro.colors.wand.*

@Preview
@Composable
fun MagicView(magic: Magic = createExampleMagic()) {
    Draggable(
        dataToDrop = magic
    ) { theMagic, _ ->
        Canvas(modifier = Modifier.size(SPELL_SIZE.dp), onDraw = {
            drawCircle(color = magic.type.color)
        })
    }
}
