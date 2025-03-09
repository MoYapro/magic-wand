package de.moyapro.colors.ui.view.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.moyapro.colors.createExampleWand
import de.moyapro.colors.game.actions.GameAction
import de.moyapro.colors.game.actions.loot.ClaimLootAction
import de.moyapro.colors.game.functions.getTag
import de.moyapro.colors.game.model.Bonk
import de.moyapro.colors.game.model.Fizz
import de.moyapro.colors.game.model.Spell
import de.moyapro.colors.game.model.Splash
import de.moyapro.colors.game.model.Wand
import de.moyapro.colors.ui.view.loot.LootView
import de.moyapro.colors.util.MAGE_II_ID
import de.moyapro.colors.util.MAGE_I_ID
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.instanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LootViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun lootViewTest() {
        val newSpells: List<Spell<*>> = listOf(Fizz(), Bonk(), Splash())
        val newWands: List<Wand> = listOf(createExampleWand(mageId = MAGE_I_ID), createExampleWand(mageId = MAGE_II_ID))
        composeTestRule.setContent {
            LootView(newSpells, newWands)
        }
        newSpells.map(::getTag).forEach { spellTag -> composeTestRule.onNode(hasTestTag(spellTag)).assertIsDisplayed() }
        newWands.map(::getTag).forEach { wandTag -> composeTestRule.onNodeWithTag(wandTag).assertExists() }
        composeTestRule.onNodeWithText("Claim").assertExists()
    }

    @Test
    fun claimSelectedSpells() {
        val newSpells: List<Spell<*>> = listOf(Fizz(), Bonk(), Splash())
        var claimAction: GameAction? = null
        val addAction = { action: GameAction -> claimAction = action }
        composeTestRule.setContent {
            LootView(newSpells, emptyList(), addAction)
        }
        composeTestRule.onNode(hasTestTag(getTag(newSpells[1])) and hasClickAction())
            .assertIsDisplayed()
            .performClick()

        composeTestRule.onNode(hasText("Claim") and hasClickAction())
            .assertIsDisplayed()
            .performClick()

        claimAction shouldBe instanceOf<ClaimLootAction>()
        (claimAction as ClaimLootAction).newSpells.single() shouldBe newSpells[1]
        (claimAction as ClaimLootAction).newWands shouldBe emptyList()
    }

    @Test
    fun claimSelectedWands() {
        val newWands: List<Wand> = listOf(createExampleWand(mageId = MAGE_I_ID), createExampleWand(mageId = MAGE_II_ID))
        var claimAction: GameAction? = null
        val addAction = { action: GameAction -> claimAction = action }
        composeTestRule.setContent {
            LootView(emptyList(), newWands, addAction)
        }
        composeTestRule.onNode(hasTestTag(getTag(newWands[0])) and hasClickAction())
            .assertIsDisplayed()
            .performClick()

        composeTestRule.onNode(hasText("Claim") and hasClickAction())
            .assertIsDisplayed()
            .performClick()

        claimAction shouldBe instanceOf<ClaimLootAction>()
        (claimAction as ClaimLootAction).newSpells shouldBe emptyList()
        (claimAction as ClaimLootAction).newWands.single() shouldBe newWands[0]
    }
}

