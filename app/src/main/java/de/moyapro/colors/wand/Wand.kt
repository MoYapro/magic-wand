package de.moyapro.colors.wand

import java.util.UUID

data class Wand private constructor(
    val id: UUID,
    val spells: List<Spell> = emptyList(),
    val magicSlots: List<MagicSlot>,
) {

    constructor(spells: List<Spell> = emptyList()) : this(UUID.randomUUID(), spells, emptyList())
    constructor(spell: Spell) : this(
        UUID.randomUUID(),
        listOf(spell),
        createMagicSlots(listOf(spell), emptyList())
    )

    private constructor(
        spells: List<Spell>,
        magicSlots: List<MagicSlot>,
        newMagic: Magic? = null
    ) : this(
        UUID.randomUUID(),
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
        magicSlots.filter(MagicSlot::full)
            .map(MagicSlot::magic) + if (null != magic) listOf(magic) else emptyList()

    val providedCountdownList = providedMagic.toMutableList()
    return requiredMagicFromSpells.map { requiredMagic ->
        if (providedCountdownList.remove(requiredMagic))
            MagicSlot(requiredMagic, true)
        else
            MagicSlot(requiredMagic, false)
    }
}
