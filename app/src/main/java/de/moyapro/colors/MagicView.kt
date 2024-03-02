package de.moyapro.colors

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import de.moyapro.colors.wand.Magic
import de.moyapro.colors.wand.MagicType

@Preview
@Composable
fun MagicView(magic: Magic = createExampleMagic()) {
    Text(
        text = "❂",
        fontSize = 66.sp,
        color = if (magic.type == MagicType.SIMPLE) Color.Blue else Color.Green,
    )
}
