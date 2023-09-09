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
        val magicGiven = if (availableMagic.isNotEmpty())
            "${availableMagic.sumOf(Magic::getValue)}"
        else
            "-"
        return "0 [ $magicGiven / ${spell.requiredResources} ] ${spell.spellName}"

    }

    fun withSpell(spell: Spell): Wand {
        return this.copy(spells = this.spells + spell)

    }

    fun placeMagic(magic: Magic): PlaceMagicResult {
        if (!hasSpaceForMagic(magic)) return PlaceMagicResult(magic, this)
        val wandWithMagic = this.copy(wandMagic = this.wandMagic + magic)
        return PlaceMagicResult(null, wandWithMagic)
    }

    private fun hasSpaceForMagic(newMagic: Magic): Boolean {
        val allAvailableMagic = this.wandMagic + newMagic
        var sumMagicAvailable = allAvailableMagic.sumOf(Magic::getValue)
        spells.forEach { spell -> sumMagicAvailable -= spell.requiredResources }

        return 0 >= sumMagicAvailable
    }

    fun canActivate(): Boolean {
        var sumMagicAvailable = this.wandMagic.sumOf(Magic::getValue)
        spells.forEach { spell -> sumMagicAvailable -= spell.requiredResources }

        return 0 <= sumMagicAvailable
    }

    fun doActivate(): List<Spell> {
        if (!canActivate()) throw IllegalStateException("cannot activate wand if it has not enough magic")
        return this.spells
    }

}
