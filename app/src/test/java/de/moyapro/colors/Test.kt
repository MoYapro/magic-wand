package de.moyapro.colors

import androidx.compose.ui.test.*
import de.moyapro.colors.game.*
import de.moyapro.colors.ui.view.components.*
import io.mockk.impl.annotations.*
import org.junit.*

class AbstractTest : AbstractComposeTest() {
    @RelaxedMockK
    private lateinit var mockViewModel: GameViewModel

    @Test
    fun helloIsDisplayed() {
        composeTestRule.setContent {
            MageView(mage = createExampleMage(mageId = MAGE_I_ID))
        }
        composeTestRule.onNodeWithText("Mage:").assertExists()
    }
}