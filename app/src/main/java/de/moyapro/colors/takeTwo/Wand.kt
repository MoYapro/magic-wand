package de.moyapro.colors.takeTwo

import de.moyapro.colors.util.mapIf
import de.moyapro.colors.wand.Magic
import de.moyapro.colors.wand.MagicSlot
import de.moyapro.colors.wand.Spell

data class Wand(
    val id: WandId = WandId(),
    val slots: List<Slot> = emptyList(),
    val fired: Boolean = false,
) {
    fun putSpell(slotId: SlotId, spell: Spell): Wand {
        return this.copy(slots = slots.mapIf({ it.id == slotId }, { it.copy(spell = spell) }))
    }

    fun putMagic(slotId: SlotId, magic: Magic): Result<Wand> {
        var placedMagic = false
        val updatedWand = Result.success(
            this.copy(slots = slots.mapIf({ !placedMagic && it.id == slotId }, {
                placedMagic = true
                it.putMagic(magic)
            }))
        )
        return if (placedMagic) updatedWand
        else Result.failure(IllegalStateException("Could not place magic. No fitting free slot found."))
    }
}


data class Slot(
    val id: SlotId = SlotId(),
    val power: Int,
    val level: Int,
    val magicSlots: List<MagicSlot>,
    val spell: Spell? = null,
) {
    fun putMagic(magic: Magic): Result<Slot> {
        var placed = false
        val updatedSlot =
            this.copy(magicSlots = magicSlots.mapIf({ !placed && it.placedMagic == null && it.requiredMagic == magic }) {
                placed = true
                it.copy(
                    placedMagic = magic
                )
            })
        return if(placed) Result.success(updatedSlot)
        else Result.failure(java.lang.IllegalStateException("Could not update slot. Magic does not fit or all places are full"))
    }
}
