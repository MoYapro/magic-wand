package de.moyapro.colors.ui.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import de.moyapro.colors.util.SPELL_SIZE

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
