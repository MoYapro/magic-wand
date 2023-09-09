package de.moyapro.colors.wand

import io.kotest.matchers.shouldBe
import org.junit.Test

internal class WandTest {

    @Test
    fun renderEmpty() {
        Wand().render() shouldBe """
            --------------
            
            --------------
        """.trimIndent()
    }

    @Test
    fun addSpell() {
        val spellName = "::SpellName::"
        val spell = Spell(spellName, 1)
        Wand().withSpell(spell).render() shouldBe """
            --------------
            0 [ - / 1 ] $spellName
            --------------
        """.trimIndent()
    }

}

data class Spell(val spellName: String, val requiredResources: Int)

data class Wand(val spells: List<Spell> = emptyList()) {
    // TODO extract to console renderer
    fun render(): String {
        val spellList: String = if (spells.isNotEmpty())
            spells.joinToString("\n") { spell -> renderSpell(spell) }
        else
            ""

        return """
            --------------
            $spellList
            --------------
        """.trimIndent()
    }

    private fun renderSpell(spell: Spell): String {
        return "0 [ - / 1 ] ${spell.spellName}"

    }

    fun withSpell(spell: Spell): Wand {
        return this.copy(spells = this.spells + spell)

    }

}
