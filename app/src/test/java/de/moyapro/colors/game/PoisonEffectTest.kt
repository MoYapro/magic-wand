package de.moyapro.colors.game

import de.moyapro.colors.game.effect.Effect
import de.moyapro.colors.game.effect.applyPoison
import de.moyapro.colors.game.enemy.blueprints.Grunt
import io.kotest.matchers.shouldBe
import org.junit.Test

class PoisonEffectTest {
    @Test
    fun `posion decrease enemy health`() {
        val poisonStrength = 2
        val enemy = Grunt().copy(statusEffects = mapOf(Effect.POISONED to poisonStrength))
        val updatedEnemy = applyPoison(enemy)
        updatedEnemy.health shouldBe enemy.health - poisonStrength
    }

    @Test
    fun `posion decreases itself when applied`() {
        val poisonStrength = 2
        val enemy = Grunt().copy(statusEffects = mapOf(Effect.POISONED to poisonStrength))
        val updatedEnemy = applyPoison(enemy)
        updatedEnemy.statusEffects[Effect.POISONED] shouldBe poisonStrength - 1
    }

    @Test
    fun `posion is removed when strenth is zero`() {
        val poisonStrength = 1
        val enemy = Grunt().copy(statusEffects = mapOf(Effect.POISONED to poisonStrength))
        val updatedEnemy = applyPoison(enemy)
        updatedEnemy.statusEffects[Effect.POISONED] shouldBe null
    }
}