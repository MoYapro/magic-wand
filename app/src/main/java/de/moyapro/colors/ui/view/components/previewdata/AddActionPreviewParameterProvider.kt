package de.moyapro.colors.ui.view.components.previewdata

import androidx.compose.ui.tooling.preview.*
import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.*

class AddActionPreviewParameterProvider(override val values: Sequence<(GameAction) -> GameViewModel> = sequenceOf({ GameViewModel() })) : PreviewParameterProvider<(GameAction) -> GameViewModel>