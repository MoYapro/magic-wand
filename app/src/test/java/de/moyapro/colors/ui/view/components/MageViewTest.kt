package de.moyapro.colors.ui.view.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.moyapro.colors.createExampleMage
import de.moyapro.colors.util.MAGE_I_ID
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MageViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun mageViewTest() {
        composeTestRule.setContent {
            MageView(mage = createExampleMage(mageId = MAGE_I_ID))
        }
        composeTestRule.onNodeWithTag("mageImage").assertExists()
    }
}