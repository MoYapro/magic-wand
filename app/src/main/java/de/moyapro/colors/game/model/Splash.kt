package de.moyapro.colors.game.model

import de.moyapro.colors.createExampleMagicSlot
import de.moyapro.colors.game.effect.Effect

class Splash(
    id: SpellId = SpellId(),
    magicSlots: List<MagicSlot> = listOf(createExampleMagicSlot(readyToZap = true)),
    effects: List<Effect> = listOf(Effect.WET),
) : Spell<Splash>(id = id, name = "Splash", magicSlots = magicSlots, effects = effects) {
    override fun copy(magicSlots: List<MagicSlot>): Splash {
        return Splash(this.id, magicSlots, this.effects)
    }
}