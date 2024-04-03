package de.moyapro.colors

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.moyapro.colors.wand.Magic

@Preview
@Composable
fun MagicView(magic: Magic = createExampleMagic(), mainViewModel: MainViewModel = MainViewModel()) {
    Draggable(
        mainViewModel = mainViewModel,
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
