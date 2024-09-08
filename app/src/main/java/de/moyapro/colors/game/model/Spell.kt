package de.moyapro.colors.game.model

import de.moyapro.colors.*
import de.moyapro.colors.util.*

data class Spell(
    val id: SpellId = SpellId(),
    val name: String,
    val magicSlots: List<MagicSlot>,
) {
    fun putMagic(magicToPlace: Magic): Result<Spell> {
        val suitableMagicSlot =
            magicSlots.firstOrNull { slot -> slot.placedMagic == null && slot.requiredMagic.type == magicToPlace.type }
                ?: return Result.failure(IllegalStateException("Could not update spell. Magic does not fit or all places are full: $magicSlots"))
        val updatedMagicSlot = suitableMagicSlot.copy(placedMagic = magicToPlace)
        val updatedSpell =
            this.copy(magicSlots = this.magicSlots.replace(suitableMagicSlot, updatedMagicSlot))
        return Result.success(updatedSpell)
    }

    fun hasRequiredMagic() = this.magicSlots.none { it.placedMagic == null }
}


fun Bonk() = Spell(name = "TestSpell", magicSlots = listOf(createExampleMagicSlot(readyToZap = true)))