package de.moyapro.colors.game.actions.fight

import de.moyapro.colors.game.model.accessor.findSlot
import de.moyapro.colors.game.model.accessor.findWand
import de.moyapro.colors.wand.getExampleGameState
import io.kotest.matchers.shouldBe
import org.junit.Test

class PlaceMagicActionTest {

    @Test
    fun placeMagicInWand() {
        val state = getExampleGameState()
        val magicToPlace = state.currentFight.magicToPlay.first()
        val wandToPlaceMagicIn = state.currentFight.wands.first()
        val slotToPlaceMagicIn = wandToPlaceMagicIn.slots.first()
        val updatedState = PlaceMagicAction(
            wandToPlaceMagicIn.id,
            slotToPlaceMagicIn.id,
            magicToPlace
        ).apply(state).getOrThrow()
        updatedState.currentFight.wands.findWand(wandToPlaceMagicIn.id)!!.slots.findSlot(slotToPlaceMagicIn.id)!!.spell!!.magicSlots.single().placedMagic shouldBe magicToPlace
    }

}