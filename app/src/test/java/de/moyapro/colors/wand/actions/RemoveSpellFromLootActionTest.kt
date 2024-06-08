package de.moyapro.colors.wand.actions

import de.moyapro.colors.*
import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.loot.*
import de.moyapro.colors.takeTwo.*
import io.kotest.matchers.*
import org.junit.*

class RemoveSpellFromLootActionTest {


    @Test
    fun removeSpellFromLoot() {
        val spell = createExampleSpell()
        val state = MyGameState(
            currentTurn = 0,
            wands = emptyList(),
            magicToPlay = emptyList(),
            enemies = emptyList(),
            mages = listOf(createExampleMage(mageId = MageId(0)), createExampleMage(mageId = MageId(1))),
            loot = Loot(spells = listOf(spell, createExampleSpell("Other")))
        )
        val action = RemoveSpellFromLootAction(spell)
        val updatedState = action.apply(state).getOrThrow()
        updatedState.loot.spells.single().name shouldBe "Other"
    }
}
