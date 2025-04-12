package de.moyapro.colors.ui.view.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
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
    val requiredMagic = magicSlot.requiredMagic
    val magicColor = requiredMagic.type.color.copy(alpha = if (magicSlot.hasRequiredMagic()) 1f else DROP_ZONE_ALPHA)
    Canvas(modifier = modifier
        .size(MAGIC_SIZE.dp)
        .testTag("${requiredMagic.id}_Magic"), onDraw = {
        drawCircle(color = magicColor)
    })
}
