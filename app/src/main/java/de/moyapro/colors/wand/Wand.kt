package de.moyapro.colors.wand

data class Wand(val spells: List<Spell> = emptyList(), val wandMagic: List<Magic> = emptyList()) {
    // TODO extract to console renderer
    fun render(): String {
        val spellList: String = if (spells.isNotEmpty())
            spells.joinToString("\n") { spell -> renderSpell(spell, wandMagic) }
        else
            ""

        return """
--------------
$spellList
--------------
        """.trimIndent()
    }

    private fun renderSpell(spell: Spell, availableMagic: List<Magic>): String {
        val magicGiven = if(availableMagic.isNotEmpty())
            "${availableMagic.size}"
        else
            "-"
        return "0 [ $magicGiven / ${spell.requiredResources} ] ${spell.spellName}"

    }

    fun withSpell(spell: Spell): Wand {
        return this.copy(spells = this.spells + spell)

    }

    fun placeMagic(magic: Magic): PlaceMagicResult {
        val wandWithMagic = this.copy(wandMagic = this.wandMagic + magic)
        return PlaceMagicResult(NO_MAGIC, wandWithMagic)
    }

}
