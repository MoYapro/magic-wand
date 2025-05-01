package de.moyapro.colors

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.generators.Initializer
import de.moyapro.colors.game.model.gameState.GameState
import de.moyapro.colors.ui.theme.ColorsTheme
import de.moyapro.colors.ui.view.stash.StashSpellsView
import de.moyapro.colors.ui.view.stash.StashWandsView
import de.moyapro.colors.ui.view.stash.WandsEditView
import de.moyapro.colors.util.FightState

@Composable
@Preview
fun StashView(
    @PreviewParameter(provider = GameStatePreviewProvider::class) gameState: GameState,
    saveAndStartFight: () -> Unit = {},
    saveAndMain: () -> Unit = {},
    printState: (GameState) -> Unit = {},
    addAction: (GameAction) -> Unit = {},
    save: () -> Unit,
    reloadState: () -> Unit,
) {
    require(gameState.currentFight.fightState == FightState.NOT_STARTED) { "Cannot stash while in fight" }
    ColorsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(), color = Color.Gray
        ) {
            Column(Modifier.fillMaxSize()) {
                StashWandsView(currentGameState = gameState, addAction = addAction)
                StashSpellsView(currentGameState = gameState, addAction = addAction)
                WandsEditView(currentGameState = gameState, addAction = addAction)
                Buttons(gameState, saveAndStartFight, saveAndMain, printState, save, reloadState)

            }
        }
    }

}

@Composable
fun Buttons(
    currentGameState: GameState,
    saveAndStartFight: () -> Unit,
    saveAndMain: () -> Unit,
    printState: (GameState) -> Unit,
    save: () -> Unit,
    reloadState: () -> Unit,
) {
    Column {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(1f / 4f)
                .border(1.dp, Color.LightGray)
        ) {
            Button(onClick = { saveAndStartFight() }) {
                Text(text = "Next fight")
            }
            Button(onClick = { saveAndMain() }) {
                Text(text = "Main menu")
            }
            Button(onClick = { printState(currentGameState) }) {
                Text(text = "Debug state")
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(1f / 4f)
                .border(1.dp, Color.LightGray)
        ) {
            Button(onClick = save) {
                Text(text = "Save")
            }
            Button(onClick = reloadState) {
                Text(text = "Reload")
            }
        }
    }

}

private class GameStatePreviewProvider : PreviewParameterProvider<GameState> {
    override val values: Sequence<GameState>
        get() = sequenceOf(Initializer.createInitialGameState())
}