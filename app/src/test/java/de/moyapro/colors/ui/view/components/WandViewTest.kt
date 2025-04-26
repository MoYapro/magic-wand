package de.moyapro.colors.ui.view.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.moyapro.colors.createExampleWand
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.generators.Initializer
import de.moyapro.colors.game.spell.Acid
import de.moyapro.colors.util.MAGE_I_ID
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
        composeTestRule.setContent {
            WandView(
                wand = createExampleWand(mageId = MAGE_I_ID, spell), addAction = dummyAddAction, currentGameState = initialGameState, clickAction = noOpClickAction
            )
        }
        composeTestRule.onNodeWithTag(testTag = "${spell.id.id}_${spell.name}", useUnmergedTree = true).assertExists().assertIsDisplayed()

    }
}