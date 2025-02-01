package de.moyapro.colors.ui.view.components

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.moyapro.colors.util.ENEMY_SIZE
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PowerMeterTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun powerMeterTest() {
        composeTestRule.setContent {
            PowerMeter(1, ENEMY_SIZE)
        }
        composeTestRule.onAllNodesWithTag("1Box").assertCountEquals(1)
    }
}