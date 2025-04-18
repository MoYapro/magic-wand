package de.moyapro.colors.game.spell

import de.moyapro.colors.createExampleMagicSlot
import de.moyapro.colors.game.effect.Effect
import de.moyapro.colors.game.model.ImageRef
import de.moyapro.colors.game.model.MagicSlot
import de.moyapro.colors.game.model.Spell
import de.moyapro.colors.game.model.SpellId
import de.moyapro.colors.R

class Bonk(
    id: SpellId = SpellId(),
    magicSlots: List<MagicSlot> = listOf(createExampleMagicSlot(readyToZap = true)),
    effects: List<Effect> = emptyList(),
) : Spell<Bonk>(id = id, name = "Bonk", magicSlots = magicSlots, effects = effects, image = ImageRef(R.drawable.bad_heart)) {
    override fun copy(magicSlots: List<MagicSlot>): Bonk {
        return Bonk(this.id, magicSlots, this.effects)
    }
}