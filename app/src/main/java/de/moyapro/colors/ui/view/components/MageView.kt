package de.moyapro.colors.ui.view.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import de.moyapro.colors.game.model.*

@Composable
fun MageView(modifier: Modifier = Modifier, mage: Mage) {
    Box(
        modifier
            .border(1.dp, Color.Blue)
    ) {
        Text(text = "Mage: ${mage.id}")
    }
}
