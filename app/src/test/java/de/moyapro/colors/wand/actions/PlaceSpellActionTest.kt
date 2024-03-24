package de.moyapro.colors.wand.actions

import de.moyapro.colors.createExampleWand
import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.game.actions.PlaceSpellAction
import de.moyapro.colors.game.actions.PlaceSpellInStashAction
import de.moyapro.colors.takeTwo.Slot
import de.moyapro.colors.wand.Spell
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.Test

class PlaceSpellActionTest {
    @Test
    fun `place spell from stash in wand`() {
        val spellToPlace = Spell(name = "newSpell")
        val wand = createExampleWand()
        val slotToPutSpellInto = wand.slots.single { it.spell?.name == "Blitz" }
        val state = MyGameState(
            wands = listOf(wand),
            currentTurn = 0,
            enemies = emptyList(),
            mages = emptyList(),
            magicToPlay = emptyList(),
            spellsInStash = listOf(spellToPlace)
        )
        val action =
            PlaceSpellAction(slotId = slotToPutSpellInto.id, spell = spellToPlace, wandId = wand.id)

        val updatedState = action.apply(state).getOrThrow()
        updatedState.findWand(wand.id)!!.slots.single { it.id == slotToPutSpellInto.id }.spell?.name shouldBe "newSpell"
        updatedState.spellsInStash.single().name shouldBe "Blitz"

    }

    @Test
    fun `place spell from slot in other slot`() {
        val wand = createExampleWand()
        val slotToPutSpellInto = wand.slots.single { it.spell?.name == "Blitz" }
        val slotToTakeSpellFrom = wand.slots.single { it.spell?.name == "Top" }
        val spellToPlace = slotToTakeSpellFrom.spell!!
        val state = MyGameState(
            wands = listOf(wand),
            currentTurn = 0,
            enemies = emptyList(),
            mages = emptyList(),
            magicToPlay = emptyList(),
            spellsInStash = emptyList()
        )
        val action =
            PlaceSpellAction(slotId = slotToPutSpellInto.id, spell = spellToPlace, wandId = wand.id)

        val updatedState = action.apply(state).getOrThrow()
        updatedState.findWand(wand.id)!!.slots.single { it.id == slotToPutSpellInto.id }.spell?.name shouldBe "Top"
        updatedState.findWand(wand.id)!!.slots.single { it.id == slotToTakeSpellFrom.id }.spell?.name shouldBe "Blitz"
        updatedState.spellsInStash shouldBe emptyList()

    }

    @Test
    fun `place a lot of spells`() {
        val wand = createExampleWand()
        val state = MyGameState(
            wands = listOf(wand),
            currentTurn = 0,
            enemies = emptyList(),
            mages = emptyList(),
            magicToPlay = emptyList(),
            spellsInStash = emptyList()
        )
        val initialSlotIds = state.wands.single().slots.map(Slot::id)
        var updatedState = state
        state.wands.first().slots.forEach { slot ->
            if (slot.spell == null) return
            val action = PlaceSpellInStashAction(slot.spell!!.id)
            updatedState = action.apply(updatedState).getOrThrow()

        }
        updatedState.spellsInStash.size shouldBe initialSlotIds.size
        updatedState.wands.single().slots.all { it.spell == null }
        updatedState.spellsInStash.forEachIndexed { index, spell ->
            val action = PlaceSpellAction(
                state.wands.single().id,
                state.wands.single().slots[index].id,
                spell
            )
            updatedState = action.apply(updatedState).getOrThrow()

        }
        updatedState.spellsInStash shouldHaveSize 0
        updatedState.wands.single().slots.all { it.spell != null } shouldBe true

        repeat(100) { iteration ->
            val sourceSlot = updatedState.wands.single().slots.random()
            val targetSlot = updatedState.wands.single().slots.random()
            check(sourceSlot.spell != null) { "Upsi: Iteration: $iteration" }
            updatedState =
                PlaceSpellAction(state.wands.single().id, targetSlot.id, sourceSlot.spell!!).apply(
                    updatedState
                ).getOrThrow()
            val updatedSourceSlot = updatedState.findSlot(sourceSlot.id)!!
            val updatedTargetSlot = updatedState.findSlot(targetSlot.id)!!
            updatedSourceSlot.spell shouldBe targetSlot.spell
            updatedTargetSlot.spell shouldBe sourceSlot.spell
            updatedSourceSlot.spell shouldNotBe null
            updatedTargetSlot.spell shouldNotBe null
        }


    }
}