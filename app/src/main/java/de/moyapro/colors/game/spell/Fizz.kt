package de.moyapro.colors.game.spell

import de.moyapro.colors.createExampleMagicSlot
import de.moyapro.colors.game.effect.Effect
import de.moyapro.colors.game.model.ImageRef
import de.moyapro.colors.game.model.MagicSlot
import de.moyapro.colors.game.model.Spell
import de.moyapro.colors.game.model.SpellId
import de.moyapro.colors.R

class Fizz(
    id: SpellId = SpellId(),
    magicSlots: List<MagicSlot> = listOf(createExampleMagicSlot(readyToZap = true)),
    effects: List<Effect> = listOf(Effect.ELECTRIFIED),
) : Spell<Fizz>(
    id = id, name = "Fizz", magicSlots = magicSlots, effects = effects, image = ImageRef(R.drawable.fizz)
) {
    override fun copy(magicSlots: List<MagicSlot>): Fizz {
        return Fizz(this.id, magicSlots, this.effects)
    }

}