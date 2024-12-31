package de.moyapro.colors.ui.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.moyapro.colors.game.model.Mage
import de.moyapro.colors.ui.view.fight.HealthView
import de.moyapro.colors.util.SPELL_SIZE
import de.moyapro.colors.R


private val mages = listOf(R.drawable.mage_blue, R.drawable.mage_green, R.drawable.mage_red)

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
            painter = painterResource(mages[mage.id.id.toInt()]), contentDescription = "Mage_blue", modifier = Modifier
                .height(SPELL_SIZE.dp)
                .width(SPELL_SIZE.dp)
        )
        Column(Modifier.size(SPELL_SIZE.dp), verticalArrangement = Arrangement.Bottom) {
            HealthView(mage.health)
        }
    }
}
