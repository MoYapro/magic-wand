package de.moyapro.colors.game.actions.loot

import de.moyapro.colors.game.model.Bonk
import de.moyapro.colors.wand.getExampleGameState
import de.moyapro.colors.wand.getExampleWandWithTwoSlots
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
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

}