package de.moyapro.colors.ui.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import de.moyapro.colors.createExampleWand
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.actions.fight.ZapAction
import de.moyapro.colors.game.model.Wand
import de.moyapro.colors.game.model.accessor.findMage
import de.moyapro.colors.game.model.gameState.GameState
import de.moyapro.colors.util.SPELL_SIZE

@Composable
fun WandAndMageView(
    modifier: Modifier = Modifier,
    wand: Wand = createExampleWand(),
    addAction: (GameAction) -> Unit,
    currentGameState: GameState,
) {
    val mage = currentGameState.currentFight.mages.findMage(wand.id)
    Column(
        modifier = modifier
            .height(4 * SPELL_SIZE.dp)
            .width(2 * SPELL_SIZE.dp)
    ) {
        if (mage != null) {
            Row(Modifier.fillMaxWidth()) {
                Button(
                    enabled = wand.hasAnySpellToZap() && mage.health > 0,
                    modifier = Modifier.width(SPELL_SIZE.dp),
                    onClick = { addAction(ZapAction(wand.id)) }
                ) { Text(text = "Zap") }
                MageView(mage = mage)
            }
        }
        WandView(modifier, wand, addAction, currentGameState)
    }
}
