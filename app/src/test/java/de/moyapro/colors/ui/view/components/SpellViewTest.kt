package de.moyapro.colors.ui.view.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.moyapro.colors.game.model.Spell
import de.moyapro.colors.game.spell.Fizz
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.nulls.shouldNotBeNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SpellViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `spellView renders`() {
        val spell: Spell<Fizz> = Fizz()
        composeTestRule.setContent {
            SpellView(spell)
        }
        composeTestRule.onNodeWithTag("${spell.id.id}_${spell.name}").assertExists()
    }

    @Test
    fun `spellView clicks`() {
        var clickedElement: Spell<*>? = null
        val click = { clickedSpell: Spell<*> -> clickedElement = clickedSpell }
        val spell: Spell<Fizz> = Fizz()
        composeTestRule.setContent {
            SpellView(spell = spell, clickAction = click)
        }
        composeTestRule.onNodeWithTag("${spell.id.id}_${spell.name}").performClick()
        clickedElement shouldNotBeNull {
            this shouldBeEqual spell
        }
    }
}

