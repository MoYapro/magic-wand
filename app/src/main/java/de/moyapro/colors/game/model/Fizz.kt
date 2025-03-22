package de.moyapro.colors.game.model

import de.moyapro.colors.createExampleMagicSlot
import de.moyapro.colors.game.effect.Effect
import de.moyapro.colors.game.enemy.Enemy
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

    override fun applyEffect(enemy: Enemy, power: Int): Enemy {
        val allEff = super.applyEffect(enemy, power)
        val currentEff = allEff.statusEffects.mapNotNull { (effect, amount) ->
            if (effect == Effect.WET) {
                if (amount <= 1) null // to remove it
                else Effect.WET to amount - 1
            } else effect to amount
        }
        return allEff.copy(statusEffects = currentEff.associate { it })
    }
}