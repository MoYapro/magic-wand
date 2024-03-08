package de.moyapro.colors

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import de.moyapro.colors.wand.MagicSlot

@Composable
fun MagicSlotView(
    magicSlot: MagicSlot = createExampleMagicSlot(),
) {
    Text(
        text = magicSlot.requiredMagic.type.symbol.toString(),
        color = if (null == magicSlot.placedMagic) magicSlot.requiredMagic.type.color.copy(alpha = .4F)
        else magicSlot.requiredMagic.type.color,
        fontSize = 20.sp,
    )
}
