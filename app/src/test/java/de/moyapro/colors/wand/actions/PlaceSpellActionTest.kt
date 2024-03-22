package de.moyapro.colors.wand.actions

import de.moyapro.colors.createExampleWand
import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.game.actions.PlaceSpellAction
import de.moyapro.colors.wand.Spell
import io.kotest.matchers.shouldBe
import org.junit.Test

class PlaceSpellActionTest {
    @Test
    fun placeSpellInWand() {
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
}