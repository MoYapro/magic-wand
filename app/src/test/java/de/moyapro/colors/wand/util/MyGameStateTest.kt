package de.moyapro.colors.wand.util

import de.moyapro.colors.*
import de.moyapro.colors.game.actions.loot.*
import de.moyapro.colors.game.generators.*
import io.kotest.matchers.*
import org.junit.*

class MyGameStateTest {

    @Test
    fun wandsInOrder() {
        val state = StartFightFactory.setupFightStage()
        val wandToAdd = createExampleWand()
        val stateWithWand = AddWandAction(wandToAdd, state.mages.first().id).apply(state).getOrThrow()
        val wandsInOrder = stateWithWand.wandsInOrder()
        state.wands.size shouldBe wandsInOrder.size
    }
}
