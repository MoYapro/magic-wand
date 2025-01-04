package de.moyapro.colors.ui.view.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.moyapro.colors.createExampleMagicSlot
import de.moyapro.colors.game.model.MagicSlot
import de.moyapro.colors.game.model.hasRequiredMagic
import de.moyapro.colors.util.DROP_ZONE_ALPHA
import de.moyapro.colors.util.MAGIC_SIZE

@Composable
@Preview
fun MagicSlotView(
    modifier: Modifier = Modifier,
    magicSlot: MagicSlot = createExampleMagicSlot(),
) {
    val magicColor = magicSlot.requiredMagic.type.color.copy(alpha = if (magicSlot.hasRequiredMagic()) 1f else DROP_ZONE_ALPHA)
    Canvas(modifier = modifier.size(MAGIC_SIZE.dp), onDraw = {
        drawCircle(color = magicColor)
    })
}
