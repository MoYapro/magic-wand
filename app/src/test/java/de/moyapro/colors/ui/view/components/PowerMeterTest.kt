package de.moyapro.colors.ui.view.components

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import de.moyapro.colors.util.ENEMY_SIZE
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner


@RunWith(ParameterizedRobolectricTestRunner::class)
class PowerMeterTest(val powerMeterValue: PowerMeterValues) {

    @get:Rule
    val composeTestRule = createComposeRule()

    private companion object {
        @JvmStatic
        @ParameterizedRobolectricTestRunner.Parameters(name = "{index}: Split power {0}")
        fun parameters() = PowerMeterValues.values()
    }

    @Test
    fun powerMeterTest() {
        composeTestRule.setContent {
            PowerMeter(power = powerMeterValue.value, ENEMY_SIZE)
        }
        composeTestRule.onAllNodesWithTag("${powerMeterValue.name}_Box").assertCountEquals(1)
    }
}


