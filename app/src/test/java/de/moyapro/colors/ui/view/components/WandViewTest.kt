package de.moyapro.colors.ui.view.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.moyapro.colors.createExampleWand
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.functions.getTag
import de.moyapro.colors.game.generators.Initializer
import de.moyapro.colors.game.model.Wand
import de.moyapro.colors.game.spell.Acid
import de.moyapro.colors.util.MAGE_I_ID
import io.kotest.matchers.shouldBe
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WandViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    val dummyAddAction: (GameAction) -> Unit = {}
    val noOpClickAction: (Any) -> Unit = {}
    val initialGameState = Initializer.createInitialGameState()

    @Test
    fun `wand view renders`() {
        val spell = Acid()
        val wand = createExampleWand(mageId = MAGE_I_ID, spell)
        composeTestRule.setContent {
            WandView(
                wand = wand, addAction = dummyAddAction, currentGameState = initialGameState, clickAction = noOpClickAction
            )
        }
        composeTestRule.onNodeWithTag(testTag = getTag(spell), useUnmergedTree = true).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(testTag = getTag(wand), useUnmergedTree = true).assertExists().assertIsDisplayed()
    }

    @Test
    fun `wand can be clicked`() {
        val spell = Acid()
        val wand = createExampleWand(mageId = MAGE_I_ID, spell)
        var clickedWand: Boolean = false
        val clickAction: (Wand) -> Unit = { clickedWand = true }

        composeTestRule.setContent {
            WandView(
                wand = wand, addAction = dummyAddAction, currentGameState = initialGameState, clickAction = clickAction
            )
        }
        composeTestRule.onNodeWithTag(testTag = getTag(wand), useUnmergedTree = true).performClick()
        clickedWand shouldBe true
    }
}