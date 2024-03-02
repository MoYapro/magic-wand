package de.moyapro.colors

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import de.moyapro.colors.wand.MagicSlot
import de.moyapro.colors.wand.MagicType

@Preview
@Composable
fun MagicSlotView(magicSlot: MagicSlot = createExampleMagicSlot()) {
    Text(
        text = "❂",
        color = if (magicSlot.requiredMagic.type == MagicType.SIMPLE) Color.Blue else Color.Green,
        fontWeight = if (null == magicSlot.placedMagic) FontWeight.ExtraBold else null
    )
}
