package de.moyapro.colors.ui.view.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.moyapro.colors.createExampleWand
import de.moyapro.colors.game.model.Bonk
import de.moyapro.colors.game.model.Fizz
import de.moyapro.colors.game.model.Spell
import de.moyapro.colors.game.model.Splash
import de.moyapro.colors.game.model.Wand
import de.moyapro.colors.ui.view.loot.LootView
import de.moyapro.colors.util.MAGE_II_ID
import de.moyapro.colors.util.MAGE_I_ID
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
        newSpells.map { spell -> "${spell.id.id}_${spell.name}" }.forEach { spellTag -> composeTestRule.onNodeWithTag(spellTag).assertExists() }
        newWands.map { wand -> "wand_${wand.id}_${wand.mageId}" }.forEach { wandTag -> composeTestRule.onNodeWithTag(wandTag).assertExists() }
    }
}

