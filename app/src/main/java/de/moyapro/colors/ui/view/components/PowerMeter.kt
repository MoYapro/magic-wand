package de.moyapro.colors.ui.view.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.unit.*
import de.moyapro.colors.util.*

@Composable
fun PowerMeter(power: Int) {
    val fives = power / 5
    val ones = power % 5
    Row {
        Spacer(modifier = Modifier.width(1.dp))
        Column(modifier = Modifier.height(SPELL_SIZE.dp), verticalArrangement = Arrangement.Bottom) {
            repeat(fives) {
                Box(
                    modifier = Modifier
                        .width(8.dp)
                        .height(6.dp)
                        .background(Color.White)
                        .testTag("5Box")
                )
                Spacer(modifier = Modifier.height(2.dp))
            }
            repeat(ones) {
                Box(
                    modifier = Modifier
                        .width(8.dp)
                        .height(2.dp)
                        .background(Color.White)
                        .testTag("1Box")
                )
                Spacer(modifier = Modifier.height(2.dp))
            }
        }
    }
}
