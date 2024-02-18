package de.moyapro.colors.wand

data class Wand private constructor(
    val spells: List<Spell> = emptyList(),
    val magicSlots: List<MagicSlot>,
) {

    constructor(spells: List<Spell> = emptyList()) : this(spells, emptyList())

    private constructor(spells: List<Spell>, magicSlots: List<MagicSlot>, newMagic: Magic?) : this(
        spells,
        createMagicSlots(spells, magicSlots, newMagic)
    )

    fun withSpell(spell: Spell): Wand {
        val newSpells = this.spells + spell
        return Wand(newSpells, magicSlots, null)
    }


    fun placeMagic(magic: Magic): PlaceMagicResult {
        if (!hasSpaceForMagic(magic)) return PlaceMagicResult(magic, this)
        val wandWithMagic = Wand(this.spells, this.magicSlots, newMagic = magic)
        return PlaceMagicResult(null, wandWithMagic)
    }

    private fun hasSpaceForMagic(newMagic: Magic): Boolean {
        val openMagicSlot = this.magicSlots.filter { slot -> !slot.full }
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

fun createMagicSlots(
    spells: List<Spell>,
    magicSlots: List<MagicSlot>,
    magic: Magic? = null
): List<MagicSlot> {
    val requiredMagicFromSpells = spells.map(Spell::requiredMagic).flatten()
    val providedMagic =
        magicSlots.map(MagicSlot::magic) + if (null != magic) listOf(magic) else emptyList()

    val fullSlots = providedMagic.map { MagicSlot(it, true) }
    val providedCountdownList = providedMagic.toMutableList()
    val emptySlots =
        requiredMagicFromSpells
            .map { required ->
                if (providedCountdownList.remove(required)) MagicSlot(
                    required
                ) else null
            }
            .filterNotNull()

    return fullSlots + emptySlots
}
