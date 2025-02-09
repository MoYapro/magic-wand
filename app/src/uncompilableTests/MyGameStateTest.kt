package de.moyapro.colors.game.util

import de.moyapro.colors.*
import de.moyapro.colors.game.actions.stash.*
import de.moyapro.colors.game.generators.*
import io.kotest.matchers.*
import org.junit.*

class NewGameStateTest {

    @Test
    fun wandsInOrder() {
        val state = StartFightFactory.setupFightStage()
        val wandToAdd = createExampleWand()
        val stateWithWand = AddWandAction(wandToAdd, state.mages.first().id).apply(state).getOrThrow()
        val wandsInOrder = stateWithWand.wandsInOrder()
        state.wands.size shouldBe wandsInOrder.size
    }
}
