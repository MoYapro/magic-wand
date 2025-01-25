package de.moyapro.colors.game.model

import de.moyapro.colors.createExampleMagicSlot
import de.moyapro.colors.game.effect.Effect

class Bonk(
    id: SpellId = SpellId(),
    magicSlots: List<MagicSlot> = listOf(createExampleMagicSlot(readyToZap = true)),
    effects: List<Effect> = emptyList(),
) : Spell<Bonk>(id = id, name = "Bonk", magicSlots = magicSlots, effects = effects) {
    override fun copy(magicSlots: List<MagicSlot>): Bonk {
        return Bonk(this.id, magicSlots, this.effects)
    }
}