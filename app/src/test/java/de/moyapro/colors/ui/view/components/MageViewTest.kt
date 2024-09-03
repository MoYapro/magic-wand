package de.moyapro.colors.ui.view.components

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.*
import androidx.test.ext.junit.runners.*
import de.moyapro.colors.*
import de.moyapro.colors.game.*
import org.junit.*
import org.junit.runner.*

@RunWith(AndroidJUnit4::class)
class MageViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun mageViewTest() {
        composeTestRule.setContent {
            MageView(mage = createExampleMage(mageId = MAGE_I_ID))
        }
        composeTestRule.onNodeWithText("Mage: $MAGE_I_ID").assertExists()
    }
}