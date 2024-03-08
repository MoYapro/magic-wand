package de.moyapro.colors.takeTwo

import de.moyapro.colors.util.mapIf
import de.moyapro.colors.util.replace
import de.moyapro.colors.wand.Magic
import de.moyapro.colors.wand.MagicSlot
import de.moyapro.colors.wand.Spell

data class Wand(
    val id: WandId = WandId(),
    val slots: List<Slot> = emptyList(),
    val zapped: Boolean = false,
) {
    fun putSpell(slotId: SlotId, spell: Spell): Wand {
        return this.copy(slots = slots.mapIf({ it.id == slotId }, { it.copy(spell = spell) }))
    }

    fun putMagic(slotId: SlotId, magic: Magic): Result<Wand> {
        var placedMagic = false
        val updatedSlots = slots.map {
            if (placedMagic || it.id != slotId) Result.success(it)
            else {
                placedMagic = true
                it.putMagic(magic)
            }
        }
        val occuredError = updatedSlots.firstOrNull { it.isFailure }?.exceptionOrNull()
        if (null != occuredError) return Result.failure(occuredError)
        val updatedWand = Result.success(
            this.copy(slots = updatedSlots.map { it.getOrThrow() })
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
    fun putMagic(magicToPlace: Magic): Result<Slot> {
        val suitableMagicSlot =
            magicSlots.firstOrNull { slot -> slot.placedMagic == null && slot.requiredMagic.type == magicToPlace.type }
                ?: return Result.failure(IllegalStateException("Could not update slot. Magic does not fit or all places are full: $magicSlots"))
        val updatedMagicSlot = suitableMagicSlot.copy(placedMagic = magicToPlace)
        val updatedSlot =
            this.copy(magicSlots = this.magicSlots.replace(suitableMagicSlot, updatedMagicSlot))
        return Result.success(updatedSlot)
    }

    fun hasRequiredMagic() = this.magicSlots.none { it.placedMagic == null }
}
