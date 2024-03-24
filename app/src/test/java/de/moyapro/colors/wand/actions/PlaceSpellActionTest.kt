package de.moyapro.colors.wand.actions

import de.moyapro.colors.createExampleWand
import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.game.actions.PlaceSpellAction
import de.moyapro.colors.wand.Spell
import io.kotest.matchers.shouldBe
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
        val slotToTakeSpellFrom =  wand.slots.single { it.spell?.name == "Top" }
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
}