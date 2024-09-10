package de.moyapro.colors.game.enemy

import de.moyapro.colors.game.effect.Effect.BURNING
import de.moyapro.colors.game.effect.Effect.WET
import io.kotest.matchers.*
import io.kotest.matchers.collections.*
import org.junit.*

class StatusEffectTest {

    @Test
    fun `can sum two status effects`() {
        val originalEffect = StatusEffect(WET, 1)
        val additionalEffect = StatusEffect(WET, 2)
        originalEffect + additionalEffect shouldBe StatusEffect(WET, 3)
    }

    @Test
    fun `can add list with differnt status effects`() {
        val originalEffect = listOf<StatusEffect>(StatusEffect(BURNING, 1))
        val additionalEffect = listOf(StatusEffect(WET, 1))
        originalEffect + additionalEffect shouldContainExactlyInAnyOrder listOf(StatusEffect(WET, 1), StatusEffect(BURNING, 1))
    }

    @Test
    fun `can add list with same status effects`() {
        val originalEffect = listOf(StatusEffect(WET, 1))
        val additionalEffect = listOf(StatusEffect(WET, 2))
        originalEffect + additionalEffect shouldBe listOf(StatusEffect(WET, 3))
    }

    @Test
    fun `can add list with mixed status effects`() {
        val originalEffect = listOf(StatusEffect(WET, 1))
        val additionalEffect = listOf(StatusEffect(WET, 2), StatusEffect(BURNING, 1))
        originalEffect + additionalEffect shouldBe listOf(StatusEffect(WET, 3), StatusEffect(BURNING, 1))
    }
}