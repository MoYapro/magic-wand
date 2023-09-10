package de.moyapro.colors.wand

data class Spell(val spellName: String, val requiredMagic: List<Magic>) {
    constructor(spellName: String, requiredMagic: Magic): this(spellName, listOf(requiredMagic))
}
