package de.moyapro.colors.wand.actions

import de.moyapro.colors.*
import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.takeTwo.*
import de.moyapro.colors.wand.*
import io.kotest.matchers.*
import io.kotest.matchers.collections.*
import org.junit.*

class PlaceSpellActionTest {
    @Test
    fun `place spell from stash in wand`() {
        val spellToPlace = Spell(name = "newSpell", magicSlots = emptyList())
        val wand = createExampleWand()
        val slotToPutSpellInto = wand.slots.single { it.spell?.name == "Blitz" }
        val state = MyGameState(
            wands = listOf(wand),
            currentTurn = 0,
            enemies = emptyList(),
            mages = emptyList(),
            magicToPlay = emptyList(),
        )
        val action =
            PlaceSpellAction(slotId = slotToPutSpellInto.id, spell = spellToPlace, wandId = wand.id)

        val updatedState = action.apply(state).getOrThrow()
        updatedState.findWand(wand.id)!!.slots.single { it.id == slotToPutSpellInto.id }.spell?.name shouldBe "newSpell"
        updatedState.loot.spells.single().name shouldBe "Blitz"

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
        )
        val action =
            PlaceSpellAction(slotId = slotToPutSpellInto.id, spell = spellToPlace, wandId = wand.id)

        val updatedState = action.apply(state).getOrThrow()
        updatedState.findWand(wand.id)!!.slots.single { it.id == slotToPutSpellInto.id }.spell?.name shouldBe "Top"
        updatedState.findWand(wand.id)!!.slots.single { it.id == slotToTakeSpellFrom.id }.spell?.name shouldBe "Blitz"
        updatedState.loot.spells shouldBe emptyList()

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
        )
        val initialSlotIds = state.wands.single().slots.map(Slot::id)
        var updatedState = state
        state.wands.first().slots.forEach { slot ->
            if (slot.spell == null) return
            val action = PlaceSpellInLootAction(slot.spell!!)
            updatedState = action.apply(updatedState).getOrThrow()

        }
        updatedState.loot.spells.size shouldBe initialSlotIds.size
        updatedState.wands.single().slots.all { it.spell == null }
        updatedState.loot.spells.forEachIndexed { index, spell ->
            val action = PlaceSpellAction(
                state.wands.single().id,
                state.wands.single().slots[index].id,
                spell
            )
            updatedState = action.apply(updatedState).getOrThrow()

        }
        updatedState.loot.spells shouldHaveSize 0
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
