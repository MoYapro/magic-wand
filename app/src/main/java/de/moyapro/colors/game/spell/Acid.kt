package de.moyapro.colors.game.spell

import de.moyapro.colors.createExampleMagicSlot
import de.moyapro.colors.game.effect.Effect
import de.moyapro.colors.game.enemy.Enemy
import de.moyapro.colors.game.model.ImageRef
import de.moyapro.colors.game.model.MagicSlot
import de.moyapro.colors.game.model.Spell
import de.moyapro.colors.game.model.SpellId
import de.moyapro.colors.R

class Acid(
    id: SpellId = SpellId(),
    magicSlots: List<MagicSlot> = listOf(createExampleMagicSlot(readyToZap = true)),
    effects: List<Effect> = listOf(Effect.POISONED),
) : Spell<Acid>(
    id = id, name = "Acid", magicSlots = magicSlots, effects = effects, image = ImageRef(R.drawable.acid)
) {
    override fun copy(magicSlots: List<MagicSlot>): Acid {
        return Acid(this.id, magicSlots, this.effects)
    }
 
    override fun damage(enemy: Enemy, power: Int): Enemy {
        return enemy
    }

    override fun applyEffect(enemy: Enemy, power: Int): Enemy {
        val allEff = super.applyEffect(enemy, power)
        val currentEff = allEff.statusEffects.mapNotNull { (effect, amount) ->
            if (effect == Effect.POISONED) {
                if (amount <= 1) null // to remove it
                else Effect.POISONED to amount - 1
            } else effect to amount
        }
        return allEff.copy(statusEffects = currentEff.associate { it })
    }
}