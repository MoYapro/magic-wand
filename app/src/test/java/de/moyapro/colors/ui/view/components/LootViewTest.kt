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
import de.moyapro.colors.game.model.Slot
import de.moyapro.colors.game.model.Spell
import de.moyapro.colors.game.model.Wand
import de.moyapro.colors.game.model.gameState.GameState
import de.moyapro.colors.game.spell.Bonk
import de.moyapro.colors.game.spell.Fizz
import de.moyapro.colors.game.spell.Splash
import de.moyapro.colors.ui.view.loot.LootView
import de.moyapro.colors.util.MAGE_II_ID
import de.moyapro.colors.util.MAGE_I_ID
import de.moyapro.colors.wand.getExampleGameState
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.instanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LootViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val gameState: GameState = getExampleGameState()

    @Test
    fun lootViewTest() {
        val newSpells: List<Spell<*>> = listOf(Fizz(), Bonk(), Splash())
        val newWands: List<Wand> = listOf(createExampleWand(mageId = MAGE_I_ID, additionalSlots = emptyArray<Slot>()), createExampleWand(mageId = MAGE_II_ID, additionalSlots = emptyArray<Slot>()))
        composeTestRule.setContent {
            LootView(newSpells, newWands, goToNextScreenAction = {}, currentGameState = gameState)
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
            LootView(newSpells, emptyList(), gameState, addAction, goToNextScreenAction = {})
        }
        composeTestRule.onNode(hasTestTag(getTag(newSpells[1])) and hasClickAction()).assertIsDisplayed().performClick()

        composeTestRule.onNode(hasText("Claim") and hasClickAction()).assertIsDisplayed().performClick()

        claimAction shouldBe instanceOf<ClaimLootAction>()
        (claimAction as ClaimLootAction).newSpells.single() shouldBe newSpells[1]
        (claimAction as ClaimLootAction).newWands shouldBe emptyList()
    }

    @Test
    fun claimSelectedWands() {
        val newWands: List<Wand> = listOf(createExampleWand(null, additionalSlots = emptyArray<Slot>()), createExampleWand(null, additionalSlots = emptyArray<Slot>()))
        var claimAction: GameAction? = null
        val addAction = { action: GameAction -> claimAction = action }
        composeTestRule.setContent {
            LootView(emptyList(), newWands, gameState, addAction, goToNextScreenAction = {})
        }
        composeTestRule.onNode(hasTestTag(getTag(newWands[0])) and hasClickAction()).assertIsDisplayed().performClick()

        composeTestRule.onNode(hasText("Claim") and hasClickAction()).assertIsDisplayed().performClick()

        claimAction shouldBe instanceOf<ClaimLootAction>()
        (claimAction as ClaimLootAction).newSpells shouldBe emptyList()
        (claimAction as ClaimLootAction).newWands.single() shouldBe newWands[0]
    }

    @Test
    fun `select - deselect wands`() {
        val newWands: List<Wand> = listOf(createExampleWand(null, additionalSlots = emptyArray<Slot>()), createExampleWand(null, additionalSlots = emptyArray<Slot>()))
        var claimAction: GameAction? = null
        val addAction = { action: GameAction -> claimAction = action }
        composeTestRule.setContent {
            LootView(emptyList(), newWands, gameState, addAction, goToNextScreenAction = {})
        }
        composeTestRule.onNode(hasTestTag(getTag(newWands[0])) and hasClickAction()).assertIsDisplayed().performClick()
        composeTestRule.onNode(hasTestTag(getTag(newWands[0])) and hasClickAction()).assertIsDisplayed().performClick()
        composeTestRule.onNode(hasText("Claim") and hasClickAction()).assertIsDisplayed().performClick()

        claimAction shouldBe instanceOf<ClaimLootAction>()
        (claimAction as ClaimLootAction).newSpells shouldBe emptyList()
        (claimAction as ClaimLootAction).newWands shouldBe emptyList()
    }

    @Test
    fun `select - deselect spells`() {
        val newSpells: List<Spell<*>> = listOf(Bonk(), Fizz())
        var claimAction: GameAction? = null
        val addAction = { action: GameAction -> claimAction = action }
        composeTestRule.setContent {
            LootView(newSpells, emptyList(), gameState, addAction, goToNextScreenAction = {})
        }
        composeTestRule.onNode(hasTestTag(getTag(newSpells[0])) and hasClickAction()).assertIsDisplayed().performClick()
        composeTestRule.onNode(hasTestTag(getTag(newSpells[0])) and hasClickAction()).assertIsDisplayed().performClick()
        composeTestRule.onNode(hasText("Claim") and hasClickAction()).assertIsDisplayed().performClick()

        claimAction shouldBe instanceOf<ClaimLootAction>()
        (claimAction as ClaimLootAction).newSpells shouldBe emptyList()
        (claimAction as ClaimLootAction).newWands shouldBe emptyList()
    }
}

