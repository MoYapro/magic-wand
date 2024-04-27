package de.moyapro.colors.wand.actions

import de.moyapro.colors.*
import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.takeTwo.*
import io.kotest.matchers.*
import io.kotest.matchers.collections.*
import org.junit.*

class AddWandToLootActionTest {
    @Test
    fun `should add wand to loot`() {
        val wand1 = createExampleWand(MageId(0))
        val wand2 = createExampleWand(MageId(1))
        wand1.mageId shouldNotBe null
        var state = MyGameState(
            currentTurn = 0,
            wands = emptyList(),
            magicToPlay = emptyList(),
            enemies = emptyList(),
            mages = emptyList(),
        )
        state = AddWandToLootAction(wand1).apply(state).getOrThrow()
        state = AddWandToLootAction(wand2).apply(state).getOrThrow()
        state.loot.wands.map(Wand::id) shouldContainExactlyInAnyOrder listOf(wand1.id, wand2.id)
        state.loot.wands.all { it.mageId == null }
    }
}
