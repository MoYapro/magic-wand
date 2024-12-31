package de.moyapro.colors.ui.view.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import de.moyapro.colors.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.ui.view.fight.*
import de.moyapro.colors.util.*

@Composable
@Preview
fun MageView(mage: Mage = Mage(health = 10)) {
    Box(
        Modifier
            .width(SPELL_SIZE.dp)
            .height(SPELL_SIZE.dp)
            .border(1.dp, Color.Black)
            .border(1.dp, Color.Blue)
    ) {
        Image(
            painter = painterResource(R.drawable.mage_blue), contentDescription = "Mage_blue", modifier = Modifier
                .height(SPELL_SIZE.dp)
                .width(SPELL_SIZE.dp)
        )
        Column(Modifier.size(SPELL_SIZE.dp), verticalArrangement = Arrangement.Bottom) {
            HealthView(mage.health)
        }
    }
}
