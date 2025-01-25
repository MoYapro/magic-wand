package de.moyapro.colors.ui.view.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.moyapro.colors.createExampleMagic
import de.moyapro.colors.game.model.Magic
import de.moyapro.colors.ui.view.dragdrop.Draggable
import de.moyapro.colors.util.SPELL_SIZE

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
