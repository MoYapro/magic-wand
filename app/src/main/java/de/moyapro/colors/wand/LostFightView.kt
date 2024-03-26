package de.moyapro.colors.wand

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun LostFightView() {
    Text("You lost the fight")
    Button(onClick = { (TODO()) }) {
        Text("Back to main menu")
    }
}