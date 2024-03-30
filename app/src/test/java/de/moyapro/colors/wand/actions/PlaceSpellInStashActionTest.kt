package de.moyapro.colors.wand.actions

import de.moyapro.colors.createExampleWand
import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.game.actions.PlaceSpellInStashAction
import de.moyapro.colors.wand.Spell
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.Test

class PlaceSpellInStashActionTest {
    @Test
    fun placeSpellInStash() {
        val wand = createExampleWand()
        val targetSlot = wand.slots.single { it.spell?.name == "Blitz" }
        val spellToPlaceInStash = targetSlot.spell!!
        val state = MyGameState(
            wands = listOf(wand),
            currentTurn = 0,
            enemies = emptyList(),
            mages = emptyList(),
            magicToPlay = emptyList(),
            spellsInStash = emptyList(),
        )
        val action = PlaceSpellInStashAction(spellToPlaceInStash.id)


        state.spellsInStash shouldBe emptyList()
        val updatedState = action.apply(state).getOrThrow()
        updatedState.spellsInStash.single().name shouldBe "Blitz"
        updatedState.wands.single().slots.single { it.id == targetSlot.id }.spell shouldBe null
    }

    @Test
    fun `two equal spells have different id`() {
        val spell1 = Spell(name = "test", magicSlots = emptyList())
        val spell2 = Spell(name = "test", magicSlots = emptyList())
        spell1.id shouldNotBe spell2.id
    }
    @Test
    fun `copied spell has same id`() {
        val spell1 = Spell(name = "test", magicSlots = emptyList())
        val spell2 = spell1.copy()
        spell1.id shouldBe spell2.id
    }
}