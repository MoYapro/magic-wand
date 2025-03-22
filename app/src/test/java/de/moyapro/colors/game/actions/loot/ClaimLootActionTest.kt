package de.moyapro.colors.game.actions.loot

import de.moyapro.colors.game.spell.Bonk
import de.moyapro.colors.wand.getExampleGameState
import de.moyapro.colors.wand.getExampleWand
import de.moyapro.colors.wand.getExampleWandWithTwoSlots
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.instanceOf
import org.junit.Test

class ClaimLootActionTest {

    @Test
    fun claimNothing() {
        val gameState = getExampleGameState()
        val updatedGameState = ClaimLootAction(newSpells = emptyList(), newWands = emptyList()).apply(gameState).getOrThrow()
        updatedGameState shouldBe gameState
    }

    @Test
    fun claimSpells() {
        val newSpells = listOf(Bonk())
        val gameState = getExampleGameState()
        val updatedGameState = ClaimLootAction(newSpells = newSpells, newWands = emptyList()).apply(gameState).getOrThrow()
        updatedGameState.currentRun.spells shouldContain newSpells.single()
        updatedGameState.currentRun.spells shouldHaveSize gameState.currentRun.spells.size + newSpells.size
    }

    @Test
    fun claimWands() {
        val newWands = listOf(getExampleWandWithTwoSlots().first)
        val gameState = getExampleGameState()
        val updatedGameState = ClaimLootAction(newSpells = emptyList(), newWands = newWands).apply(gameState).getOrThrow()
        updatedGameState.currentRun.wandsInBag shouldContain newWands.single()
        updatedGameState.currentRun.wandsInBag shouldHaveSize gameState.currentRun.wandsInBag.size + newWands.size
    }

    @Test
    fun disallowDuplicateSpells() {
        val spell = Bonk()
        val newSpells = listOf(spell, spell)
        val gameState = getExampleGameState()
        val updatedResult = ClaimLootAction(newSpells = newSpells, newWands = emptyList()).apply(gameState)
        updatedResult.isFailure shouldBe true
        updatedResult.exceptionOrNull() shouldBe instanceOf<IllegalArgumentException>()
    }

    @Test
    fun disallowDuplicateWands() {
        val wand = getExampleWand()
        val newWands = listOf(wand, wand)
        val gameState = getExampleGameState()
        val updatedResult = ClaimLootAction(newSpells = emptyList(), newWands = newWands).apply(gameState)
        updatedResult.isFailure shouldBe true
        updatedResult.exceptionOrNull() shouldBe instanceOf<IllegalArgumentException>()
    }

}