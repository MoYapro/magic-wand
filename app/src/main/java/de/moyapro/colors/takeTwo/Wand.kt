package de.moyapro.colors.takeTwo

import de.moyapro.colors.util.mapIf
import de.moyapro.colors.wand.Magic
import de.moyapro.colors.wand.Spell

data class Wand(
    override val id: WandId = WandId(),
    val slots: List<Slot> = emptyList(),
    val zapped: Boolean = false,
    val mageId: MageId? = null,
) : HasId<WandId> {
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


