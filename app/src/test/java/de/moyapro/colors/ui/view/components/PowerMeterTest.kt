package de.moyapro.colors.ui.view.components

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.*
import androidx.test.ext.junit.runners.*
import org.junit.*
import org.junit.runner.*

@RunWith(AndroidJUnit4::class)
class PowerMeterTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun powerMeterTest() {
        composeTestRule.setContent {
            PowerMeter(1)
        }
        composeTestRule.onAllNodesWithTag("1Box").assertCountEquals(1)
    }
}