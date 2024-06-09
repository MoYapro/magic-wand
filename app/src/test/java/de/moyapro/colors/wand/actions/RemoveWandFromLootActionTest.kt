package de.moyapro.colors.wand.actions

import de.moyapro.colors.*
import de.moyapro.colors.game.actions.loot.*
import de.moyapro.colors.game.model.*
import io.kotest.matchers.*
import org.junit.*

class RemoveWandFromLootActionTest {
    @Test
    fun `should remove wand from loot`() {
        val wand1 = createExampleWand()
        val wand2 = createExampleWand()
        val state =
            AddWandToLootAction(wand1).apply(
                AddWandToLootAction(wand2).apply(
                    MyGameState(
                        currentTurn = 0,
                        wands = emptyList(),
                        magicToPlay = emptyList(),
                        enemies = emptyList(),
                        mages = emptyList(),
                    )
                ).getOrThrow()
            ).getOrThrow()
        val updatedState = RemoveWandFromLootAction(wand1).apply(state)
        updatedState.getOrThrow().loot.wands.single() shouldBe wand2
    }
}
