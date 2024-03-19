package de.moyapro.colors

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import de.moyapro.colors.ui.theme.ColorsTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColorsTheme {
                MainMenu(
                    listOf(
                        "Start fight" to ::startFightActivity,
                        "Edit wands" to ::startEditWandsActivity
                    )
                )
            }
        }
    }


    private fun startFightActivity() {
        this.startActivity(Intent(this, FightActivity::class.java))
    }

    private fun startEditWandsActivity() {
        this.startActivity(Intent(this, EditWandsActivity::class.java))
    }

}
