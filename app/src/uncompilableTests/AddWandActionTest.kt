package de.moyapro.colors.wand.actions

import de.moyapro.colors.*
import de.moyapro.colors.game.actions.loot.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.gameState.*
import io.kotest.assertions.throwables.*
import io.kotest.matchers.*
import io.kotest.matchers.collections.*
import org.junit.*

class AddWandActionTest {
    @Test
    fun `should add wand`() {
        val wand1 = createExampleWand()
        val wand2 = createExampleWand()
        var state = NewGameState(
            currentTurn = 0,
            wands = emptyList(),
            magicToPlay = emptyList(),
            enemies = emptyList(),
            mages = listOf(createExampleMage(mageId = MageId(0)), createExampleMage(mageId = MageId(1))),
        )
        state = AddWandAction(targetMageId = MageId(0), wandToAdd = wand1).apply(state).getOrThrow()
        state = AddWandAction(targetMageId = MageId(1), wandToAdd = wand2).apply(state).getOrThrow()
        state.wands shouldContainExactlyInAnyOrder listOf(wand1.copy(mageId = MageId(0)), wand2.copy(mageId = MageId(1)))
        state.findMage(wand1.id)?.id shouldBe MageId(0)
        state.findMage(wand2.id)?.id shouldBe MageId(1)
    }

    @Test
    fun `should throw when replace wand`() {
        val wand1 = createExampleWand()
        val wand2 = createExampleWand()
        var state = NewGameState(
            currentTurn = 0,
            wands = emptyList(),
            magicToPlay = emptyList(),
            enemies = emptyList(),
            mages = listOf(createExampleMage(mageId = MageId(0)), createExampleMage(mageId = MageId(1))),
        )
        state = AddWandAction(targetMageId = MageId(0), wandToAdd = wand1).apply(state).getOrThrow()
        shouldThrow<IllegalArgumentException> {
            AddWandAction(targetMageId = MageId(0), wandToAdd = wand2).apply(state).getOrThrow()
        }

    }


}
