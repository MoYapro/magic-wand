package de.moyapro.colors.game

import de.moyapro.colors.game.effect.Effect
import de.moyapro.colors.game.effect.applyWet
import de.moyapro.colors.game.enemy.blueprints.Grunt
import io.kotest.matchers.shouldBe
import org.junit.Test

class WetEffectTest {
    @Test
    fun `wet leaves enemy health untouched`() {
        val wetStrength = 2
        val enemy = Grunt().copy(statusEffects = mapOf(Effect.WET to wetStrength))
        val updatedEnemy = applyWet(enemy)
        updatedEnemy.health shouldBe enemy.health
    }

    @Test
    fun `wet decreases itself when applied`() {
        val wetStrength = 2
        val enemy = Grunt().copy(statusEffects = mapOf(Effect.WET to wetStrength))
        val updatedEnemy = applyWet(enemy)
        updatedEnemy.statusEffects[Effect.WET] shouldBe wetStrength - 1
    }

    @Test
    fun `wet greater burning`() {
        val wetStrength = 20
        val fireStrength = 10
        val enemy = Grunt().copy(statusEffects = mapOf(Effect.WET to wetStrength, Effect.BURNING to fireStrength))
        val updatedEnemy = applyWet(enemy)
        updatedEnemy.statusEffects[Effect.WET] shouldBe wetStrength - 10
        updatedEnemy.statusEffects[Effect.BURNING] shouldBe null
    }

    @Test
    fun `burning greater wet`() {
        val wetStrength = 10
        val fireStrength = 20
        val enemy = Grunt().copy(statusEffects = mapOf(Effect.WET to wetStrength, Effect.BURNING to fireStrength))
        val updatedEnemy = applyWet(enemy)
        updatedEnemy.statusEffects[Effect.WET] shouldBe null
        updatedEnemy.statusEffects[Effect.BURNING] shouldBe 10
    }

    @Test
    fun `wet is removed when strenth is zero`() {
        val wetStrength = 1
        val enemy = Grunt().copy(statusEffects = mapOf(Effect.WET to wetStrength))
        val updatedEnemy = applyWet(enemy)
        updatedEnemy.statusEffects[Effect.WET] shouldBe null
    }
}