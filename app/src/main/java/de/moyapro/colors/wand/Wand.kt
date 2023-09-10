package de.moyapro.colors.wand

data class Wand(val spells: List<Spell> = emptyList(), val wandMagic: List<Magic> = emptyList()) {

    private val magicSlots: List<MagicSlot>

    init {
        magicSlots = spells.map(Spell::requiredMagic).flatten().map { magic -> MagicSlot(magic) }
    }

    fun withSpell(spell: Spell): Wand {
        return this.copy(spells = this.spells + spell)
    }

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
        return "0 [ $magicGiven / ${spell.requiredMagic.size} ] ${spell.spellName}"

    }


    fun placeMagic(magic: Magic): PlaceMagicResult {
        if (!hasSpaceForMagic(magic)) return PlaceMagicResult(magic, this)
        val wandWithMagic = this.copy(wandMagic = this.wandMagic + magic)
        return PlaceMagicResult(null, wandWithMagic)
    }

    private fun hasSpaceForMagic(newMagic: Magic): Boolean {
        val openMagicSlot = this.magicSlots.filter { slot -> slot.full }
        return openMagicSlot.any { slot -> slot.magic == newMagic }
    }

    fun canActivate(): Boolean {
        return magicSlots.all(MagicSlot::full)
    }

    fun doActivate(): List<Spell> {
        if (!canActivate()) throw IllegalStateException("cannot activate wand if it has not enough magic")
        return this.spells
    }

}
