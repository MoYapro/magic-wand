package de.moyapro.colors.wand

import io.kotest.matchers.shouldBe
import org.junit.Test

class GemTest {

    private val spellName = "::SpellName::"

    private val spell = Spell(spellName)

    @Test
    fun create() {
        val gem = Gem().withSpell(spell)
        gem.spells[0] shouldBe spell
    }

    @Test
    fun generateOneMagic() {
        Gem().withSpell(spell).generate() shouldBe listOf(1)
    }

}
