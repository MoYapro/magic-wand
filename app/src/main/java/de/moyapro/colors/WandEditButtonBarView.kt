package de.moyapro.colors

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun WandEditButtonBarView(saveWands: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Button(onClick = { TODO() }) {
            Text("Undo")
        }
        Button(onClick = { TODO() }) {
            Text("discard changes")
        }
        Button(onClick = saveWands) {
            Text("save wands")
        }
    }
}