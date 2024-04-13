package de.moyapro.colors.wand

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun WinFightView(startMainActivity: () -> Unit) {
    Text("You won the fight")
    Button(onClick = startMainActivity) {
        Text("Back to main menu")
    }
}
