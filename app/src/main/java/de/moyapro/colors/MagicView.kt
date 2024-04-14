package de.moyapro.colors

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import de.moyapro.colors.wand.*

@Preview
@Composable
fun MagicView(magic: Magic = createExampleMagic()) {
    Draggable(
        dataToDrop = magic
    ) {
        Box(
            Modifier
                .size(2.dp)
                .background(Color.Black))
        Text(
            text = magic.type.symbol.toString(),
            fontSize = 66.sp,
            color = magic.type.color
        )
    }
}
