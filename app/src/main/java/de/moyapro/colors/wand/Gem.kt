package de.moyapro.colors.wand

data class Gem(val spells: List<Spell> = emptyList()) {
    fun withSpell(spell: Spell): Gem {
        return this.copy(spells = this.spells + spell)
    }

    fun generate(): List<Int> {
        return listOf(1)
    }


}
