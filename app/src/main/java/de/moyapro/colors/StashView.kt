package de.moyapro.colors

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import de.moyapro.colors.game.GameViewModel
import de.moyapro.colors.game.generators.Initializer
import de.moyapro.colors.game.model.gameState.GameState
import de.moyapro.colors.ui.theme.ColorsTheme
import de.moyapro.colors.ui.view.stash.StashSpellsView
import de.moyapro.colors.ui.view.stash.StashWandsView
import de.moyapro.colors.ui.view.stash.WandsEditView
import de.moyapro.colors.util.SPELL_SIZE

@Composable
@Preview
fun StashView(
    @PreviewParameter(provider = GameViewModelPreviewProvider::class) gameViewModel: GameViewModel,
    saveAndStartFight: (GameState) -> Unit = {},
    saveAndMain: (GameState) -> Unit = {},
    printState: (GameState) -> Unit = {},
) {

    val currentGameStateResult: Result<GameState> by gameViewModel.uiState.collectAsState()

    val currentGameState: GameState = currentGameStateResult.getOrElse {
        Toast.makeText(LocalContext.current, it.message, Toast.LENGTH_LONG).show()
        Initializer.createInitialGameState()
    }
    ColorsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Gray
        ) {
            Column(Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3 * SPELL_SIZE.dp)
                        .border(1.dp, Color.LightGray)
                ) {
                    StashWandsView(
                        currentGameState = currentGameState,
                        addAction = gameViewModel::addAction
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2 * SPELL_SIZE.dp)
                        .border(1.dp, Color.LightGray)
                ) {
                    StashSpellsView(currentGameState = currentGameState, addAction = gameViewModel::addAction)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3 * SPELL_SIZE.dp)
                        .border(1.dp, Color.LightGray)
                ) {
                    WandsEditView(
                        currentGameState = currentGameState,
                        addAction = gameViewModel::addAction
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(1f / 4f)
                        .border(1.dp, Color.LightGray)
                ) {
                    Button(onClick = { saveAndStartFight(currentGameState) }) {
                        Text(text = "Next fight")
                    }
                    Button(onClick = { saveAndMain(currentGameState) }) {
                        Text(text = "Main menu")
                    }
                    Button(onClick = { printState(currentGameState) }) {
                        Text(text = "Debug state")
                    }
                }
            }
        }
    }

}

private class GameViewModelPreviewProvider : PreviewParameterProvider<GameViewModel> {
    override val values: Sequence<GameViewModel>
        get() = sequenceOf(GameViewModel(
            saveActions = {},
            loadActions = { emptyList() },
            saveState = { _, _ -> Unit }
        ))
}