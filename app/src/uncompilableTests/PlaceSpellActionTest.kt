package de.moyapro.colors.wand.actions

import de.moyapro.colors.*
import de.moyapro.colors.game.actions.loot.*
import de.moyapro.colors.game.model.*
import io.kotest.matchers.*
import org.junit.*

class PlaceSpellActionTest {
    @Test
    fun `place spell in wand at hand`() {
        val spellToPlace = Spell(name = "newSpell", magicSlots = emptyList())
        val mageId = MageId(0)
        val wand = createExampleWand(mageId = mageId)
        val slotToPutSpellInto = wand.slots.single { it.spell?.name == "Blitz" }
        val state = NewGameState(
            wands = listOf(wand),
            currentTurn = 0,
            enemies = emptyList(),
            mages = listOf(createExampleMage(mageId = mageId, wandId = wand.id)),
            magicToPlay = emptyList(),
        )
        val action =
            PlaceSpellAction(slotId = slotToPutSpellInto.id, spell = spellToPlace, wandId = wand.id)

        val updatedState = action.apply(state).getOrThrow()
        updatedState.findWand(wand.id)!!.slots.single { it.id == slotToPutSpellInto.id }.spell?.name shouldBe "newSpell"
    }

    @Test
    fun `place spell in wand in loot`() {
        val spellToPlace = Spell(name = "newSpell", magicSlots = emptyList())
        val mageId = MageId(0)
        val wand = createExampleWand(mageId = mageId)
        val slotToPutSpellInto = wand.slots.single { it.spell?.name == "Blitz" }
        val state = NewGameState(
            wands = emptyList(),
            currentTurn = 0,
            enemies = emptyList(),
            mages = listOf(createExampleMage(mageId = mageId, wandId = wand.id)),
            magicToPlay = emptyList(),
            loot = Loot(wands = listOf(wand))
        )
        val action =
            PlaceSpellAction(slotId = slotToPutSpellInto.id, spell = spellToPlace, wandId = wand.id)

        val updatedState = action.apply(state).getOrThrow()
        updatedState.loot.findWand(wand.id)!!.slots.single { it.id == slotToPutSpellInto.id }.spell?.name shouldBe "newSpell"
    }

}
