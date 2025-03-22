package de.moyapro.colors.ui.view.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.moyapro.colors.game.model.Spell
import de.moyapro.colors.game.spell.Fizz
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SpellViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun spellViewTest() {
        val spell: Spell<Fizz> = Fizz()
        composeTestRule.setContent {
            SpellView(spell)
        }
        composeTestRule.onNodeWithTag("${spell.id.id}_${spell.name}").assertExists()
    }
}

