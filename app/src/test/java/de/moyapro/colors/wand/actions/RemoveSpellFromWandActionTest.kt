package de.moyapro.colors.wand.actions

import de.moyapro.colors.*
import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.*
import io.kotest.matchers.*
import org.junit.*

class RemoveSpellFromWandActionTest {
    @Test
    fun `remove spell from wand in hand`() {
        val wand = createExampleWand()
        val slotToRemoveSpellFrom = wand.slots.single { it.spell?.name == "Blitz" }
        val state = MyGameState(
            wands = listOf(wand),
            currentTurn = 0,
            enemies = emptyList(),
            mages = emptyList(),
            magicToPlay = emptyList(),
        )
        val action = RemoveSpellFromWandAction(slotId = slotToRemoveSpellFrom.id, spell = slotToRemoveSpellFrom.spell!!, wandId = wand.id)

        val updatedState = action.apply(state).getOrThrow()
        updatedState.findWand(wand.id)!!.slots.single { it.id == slotToRemoveSpellFrom.id }.spell shouldBe null
    }

    @Test
    fun `remove spell from wand in loot`() {
        val wand = createExampleWand()
        val slotToRemoveSpellFrom = wand.slots.single { it.spell?.name == "Blitz" }
        val state = MyGameState(
            wands = emptyList(),
            currentTurn = 0,
            enemies = emptyList(),
            mages = emptyList(),
            magicToPlay = emptyList(),
            loot = Loot(wands = listOf(wand))
        )
        val action = RemoveSpellFromWandAction(slotId = slotToRemoveSpellFrom.id, spell = slotToRemoveSpellFrom.spell!!, wandId = wand.id)

        val updatedState = action.apply(state).getOrThrow()
        updatedState.loot.findWand(wand.id)!!.slots.single { it.id == slotToRemoveSpellFrom.id }.spell shouldBe null
    }

}
