package de.moyapro.colors.ui.view.fight

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider


@Composable
@Preview
fun HealthView(@PreviewParameter(provider = HealthProvider::class) health: Int) {
    Row {
        if (health <= 0) {
            Text("💔")
        } else {
            Text(text = "♥", color = Color.Green)
            Text(" x $health", color = Color.Green)
        }
    }
}

class HealthProvider : PreviewParameterProvider<Int> {
    override val values: Sequence<Int>
        get() = sequenceOf(1, 10, 444, 1213, 0, -1)

}
