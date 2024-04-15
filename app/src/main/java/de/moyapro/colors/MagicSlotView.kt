package de.moyapro.colors

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.unit.*
import de.moyapro.colors.util.*
import de.moyapro.colors.wand.*

@Composable
fun MagicSlotView(
    magicSlot: MagicSlot = createExampleMagicSlot(),
) {
    val image = painterResource(R.drawable.bad_heart)
    Image(
        painter = image,
        contentDescription = "Name",
        modifier = Modifier.height(SPELL_SIZE.dp).width(SPELL_SIZE.dp)
    )
    Text(
        text = magicSlot.requiredMagic.type.symbol.toString(),
        color = if (null == magicSlot.placedMagic) magicSlot.requiredMagic.type.color.copy(alpha = .4F)
        else magicSlot.requiredMagic.type.color,
        fontSize = 20.sp,
    )
}
