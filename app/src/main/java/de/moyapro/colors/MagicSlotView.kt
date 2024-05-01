package de.moyapro.colors

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import de.moyapro.colors.wand.*

@Composable
@Preview
fun MagicSlotView(
    magicSlot: MagicSlot = createExampleMagicSlot(),
) {
    Text(
        modifier = Modifier,
        text = magicSlot.requiredMagic.type.symbol.toString(),
        color = if (null == magicSlot.placedMagic) magicSlot.requiredMagic.type.color.copy(alpha = .4F)
        else magicSlot.requiredMagic.type.color,
        fontSize = 20.sp,
        lineHeight = 18.sp
    )
}
