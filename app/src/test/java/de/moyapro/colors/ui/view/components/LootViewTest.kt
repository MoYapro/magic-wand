package de.moyapro.colors.ui.view.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LootViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun lootViewTest() {
        composeTestRule.setContent {
//            LootView()
        }
        composeTestRule.onNodeWithTag("mageImage").assertExists()
    }
}