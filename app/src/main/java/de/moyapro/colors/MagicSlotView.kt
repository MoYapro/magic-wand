package de.moyapro.colors

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.moyapro.colors.game.GameViewModel
import de.moyapro.colors.game.actions.PlaceMagicAction
import de.moyapro.colors.takeTwo.SlotId
import de.moyapro.colors.takeTwo.WandId
import de.moyapro.colors.wand.Magic
import de.moyapro.colors.wand.MagicSlot
import de.moyapro.colors.wand.MagicType

@Composable
fun MagicSlotView(wandId: WandId, slotId: SlotId, magicSlot: MagicSlot = createExampleMagicSlot(), gameViewModel: GameViewModel) {
    DropZone<Magic>(
        modifier = Modifier
            .size(Dp(24f))
            .border(BorderStroke(1.dp, Color.Black))
    ) { isInBound: Boolean, droppedMagic: Magic? ->
        val localContext = LocalContext.current
        if (droppedMagic != null)
            LaunchedEffect(key1 = droppedMagic) {
                gameViewModel.addAction(PlaceMagicAction(wandId, slotId, droppedMagic))
                Toast.makeText(localContext, droppedMagic.toString(), Toast.LENGTH_LONG)
                    .show()
            }



        Text(
            modifier = Modifier.background(if (isInBound) Color.Red else Color.Transparent),
            text = "❂",
            color = if (magicSlot.requiredMagic.type == MagicType.SIMPLE) Color.Blue else Color.Green,
            fontWeight = if (null == magicSlot.placedMagic) FontWeight.ExtraBold else null,
            fontSize = 20.sp,
        )
    }
}
