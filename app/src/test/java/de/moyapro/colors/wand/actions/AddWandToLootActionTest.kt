package de.moyapro.colors.wand.actions

import de.moyapro.colors.createExampleWand
import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.game.actions.AddWandToLootAction
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import org.junit.Test

class AddWandToLootActionTest {
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
        state.loot.wands shouldContainExactlyInAnyOrder listOf(wand1, wand2)
    }
}
