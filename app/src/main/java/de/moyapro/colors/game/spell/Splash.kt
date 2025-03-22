package de.moyapro.colors.game.spell

import de.moyapro.colors.createExampleMagicSlot
import de.moyapro.colors.game.effect.Effect
import de.moyapro.colors.game.model.ImageRef
import de.moyapro.colors.game.model.MagicSlot
import de.moyapro.colors.game.model.Spell
import de.moyapro.colors.game.model.SpellId
import de.moyapro.colors.R

class Splash(
    id: SpellId = SpellId(),
    magicSlots: List<MagicSlot> = listOf(createExampleMagicSlot(readyToZap = true)),
    effects: List<Effect> = listOf(Effect.WET),
) : Spell<Splash>(id = id, name = "Splash", magicSlots = magicSlots, effects = effects, image = ImageRef(R.drawable.splash)) {
    override fun copy(magicSlots: List<MagicSlot>): Splash {
        return Splash(this.id, magicSlots, this.effects)
    }
}