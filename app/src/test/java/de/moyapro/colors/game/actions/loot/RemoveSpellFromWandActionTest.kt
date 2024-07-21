package de.moyapro.colors.game.actions

import de.moyapro.colors.game.actions.loot.*
import de.moyapro.colors.game.model.accessor.*
import de.moyapro.colors.game.*
import io.kotest.matchers.*
import org.junit.*

class RemoveSpellFromWandActionTest {
    @Test
    fun `remove spell from wand in hand`() {
        val state = getExampleGameState()
        val wandToRemoveSpellFrom = state.currentRun.activeWands.first()
        val slotToRemoveSpellFrom = wandToRemoveSpellFrom.slots.first()
        val action = RemoveSpellFromWandAction(slotId = slotToRemoveSpellFrom.id, wandId = wandToRemoveSpellFrom.id)
        val updatedState = action.apply(state).getOrThrow()
        updatedState.currentRun.activeWands.findWand(wandToRemoveSpellFrom.id)!!.slots.single { it.id == slotToRemoveSpellFrom.id }.spell shouldBe null
        updatedState.currentRun.wandsInBag shouldBe state.currentRun.wandsInBag
    }

    @Test
    fun `remove spell from wand in loot`() {
        val state = getExampleGameState()
        val wandToRemoveSpellFrom = state.currentRun.wandsInBag.first()
        val slotToRemoveSpellFrom = wandToRemoveSpellFrom.slots.first()
        val action = RemoveSpellFromWandAction(slotId = slotToRemoveSpellFrom.id, wandId = wandToRemoveSpellFrom.id)
        val updatedState = action.apply(state).getOrThrow()
        updatedState.currentRun.wandsInBag.findWand(wandToRemoveSpellFrom.id)!!.slots.single { it.id == slotToRemoveSpellFrom.id }.spell shouldBe null
        updatedState.currentRun.activeWands shouldBe state.currentRun.activeWands
    }

}
