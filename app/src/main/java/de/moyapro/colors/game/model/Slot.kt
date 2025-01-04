package de.moyapro.colors.game.model

import de.moyapro.colors.game.model.interfaces.HasId

data class Slot(
    override val id: SlotId = SlotId(),
    val power: Int,
    val level: Int,
    val spell: Spell<*>? = null,
) : HasId<SlotId> {
    fun putMagic(magicToPlace: Magic): Result<Slot> {
        if (null == spell) return Result.failure(IllegalStateException("Slot does not have a spell"))
        val updatedSpell = spell.putMagic(magicToPlace)
        if (updatedSpell.isFailure) return Result.failure(updatedSpell.exceptionOrNull()!!)
        val updatedSlot = this.copy(spell = updatedSpell.getOrThrow())
        return Result.success(updatedSlot)
    }

    fun hasRequiredMagic() = this.spell?.hasRequiredMagic() ?: false

    fun canPlace(magicToPlace: Magic) = spell?.magicSlots?.any { it.placedMagic == null && it.requiredMagic.type == magicToPlace.type } ?: false
}
