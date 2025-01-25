package de.moyapro.colors.ui.view.components.previewdata

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class BattleBoardPreviewProviderModifier(override val values: Sequence<Modifier> = sequenceOf(Modifier.fillMaxHeight(1F / 3F))) : PreviewParameterProvider<Modifier>
