package de.moyapro.colors.ui.view.mainmenu

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*

@Composable
fun MainMenu(
    menuActions: List<Pair<String, () -> Unit>>,
) {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            menuActions.forEach { (lable, action) ->

                Button(onClick = action) {
                    Text(lable)
                }
            }

        }
    }
}


