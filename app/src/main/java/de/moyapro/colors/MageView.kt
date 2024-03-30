package de.moyapro.colors

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.moyapro.colors.takeTwo.Mage

@Composable
fun MageView(mage: Mage) {
    Box(
        Modifier
            .width(72.dp)
            .height(72.dp)
            .border(1.dp, Color.Blue)
    ) {

    }
}
