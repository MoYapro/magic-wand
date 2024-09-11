package de.moyapro.colors.game.model

import com.fasterxml.jackson.annotation.*
import de.moyapro.colors.game.effect.*
import de.moyapro.colors.game.enemy.*
import de.moyapro.colors.util.*

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type")
@JsonSubTypes(
    JsonSubTypes.Type(value = Bonk::class, name = "Bonk"),
    JsonSubTypes.Type(value = Splash::class, name = "Splash"),
)
abstract class Spell<SPELL_TYPE : Spell<SPELL_TYPE>>(
    val id: SpellId = SpellId(),
    val name: String,
    val magicSlots: List<MagicSlot>,
    val effects: List<Effect> = emptyList(),
) {

    @JsonProperty("@type")
    private val type = this.javaClass.simpleName

    fun putMagic(magicToPlace: Magic): Result<SPELL_TYPE> {
        val suitableMagicSlot =
            magicSlots.firstOrNull { slot -> slot.placedMagic == null && slot.requiredMagic.type == magicToPlace.type }
                ?: return Result.failure(IllegalStateException("Could not update spell. Magic does not fit or all places are full: $magicSlots"))
        val updatedMagicSlot = suitableMagicSlot.copy(placedMagic = magicToPlace)
        val updatedSpell =
            this.copy(magicSlots = this.magicSlots.replace(suitableMagicSlot, updatedMagicSlot))
        return Result.success(updatedSpell)
    }

    fun hasRequiredMagic() = this.magicSlots.none { it.placedMagic == null }

    abstract fun copy(magicSlots: List<MagicSlot>): SPELL_TYPE

    open fun damage(enemy: Enemy, power: Int): Enemy {
        return enemy.copy(health = enemy.health - power)
    }


    open fun applyEffect(enemy: Enemy, power: Int): Enemy {
        val updatedStatusEffects: Map<Effect, Int> = enemy.statusEffects + this.effects.associateWith { _ -> power }
        return enemy.copy(statusEffects = updatedStatusEffects)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Spell<*>) return false

        if (id != other.id) return false
        if (name != other.name) return false
        if (magicSlots != other.magicSlots) return false
        if (effects != other.effects) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 67 * result + magicSlots.hashCode()
        result = 151 * result + effects.hashCode()
        result = 23 * result + (type.hashCode())
        return result
    }
}
