package de.moyapro.colors

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import de.moyapro.colors.util.*
import de.moyapro.colors.wand.*

@Composable
@Preview
fun MagicSlotView(
    modifier: Modifier = Modifier,
    magicSlot: MagicSlot = createExampleMagicSlot(),
) {
    val magicColor = magicSlot.requiredMagic.type.color.copy(alpha = if (!magicSlot.hasRequiredMagic()) 1f else DROP_ZONE_ALPHA)
    Canvas(modifier = Modifier.size(MAGIC_SIZE.dp), onDraw = {
        drawCircle(color = magicColor)
    })
}
