package de.moyapro.colors.wand.actions

import de.moyapro.colors.*
import de.moyapro.colors.game.actions.loot.*
import de.moyapro.colors.game.model.*
import io.kotest.matchers.*
import org.junit.*

class PlaceSpellInLootActionTest {
    @Test
    fun placeSpellInLoot() {
        val wand = createExampleWand()
        val targetSlot = wand.slots.single { it.spell?.name == "Blitz" }
        val spellToPlaceInLoot = targetSlot.spell!!
        val state = NewGameState(
            wands = listOf(wand),
            currentTurn = 0,
            enemies = emptyList(),
            mages = emptyList(),
            magicToPlay = emptyList(),
        )
        val action = PlaceSpellInLootAction(spellToPlaceInLoot)
        state.loot.spells shouldBe emptyList()
        val updatedState = action.apply(state).getOrThrow()
        updatedState.loot.spells.single().name shouldBe "Blitz"
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
