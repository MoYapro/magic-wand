package de.moyapro.colors

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.moyapro.colors.takeTwo.Mage

@Composable
fun MageView(modifier: Modifier = Modifier, mage: Mage) {
    Box(
        modifier
            .border(1.dp, Color.Blue)
    ) {

    }
}
