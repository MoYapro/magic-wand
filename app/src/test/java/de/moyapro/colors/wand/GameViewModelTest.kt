package de.moyapro.colors.wand

import de.moyapro.colors.game.AddWandAction
import de.moyapro.colors.game.GameViewModel
import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.game.PlaceMagicAction
import io.kotest.matchers.shouldBe
import org.junit.Test

internal class GameViewModelTest {
    @Test
    fun initEmpty() {
        val gameViewModel = GameViewModel()
        gameViewModel.getCurrentGameState() shouldBe MyGameState(emptyList())
    }

    @Test
    fun addGemToWandAction() {
        val gameViewModel = GameViewModel()
        val (wand, slot) = getExampleWandWithSingleSlot()
        val magicToPutIn = Magic(MagicType.SIMPLE)
        gameViewModel
            .addAction(AddWandAction(wand))
            .addAction(PlaceMagicAction(wand.id, slot.id, magicToPutIn))
            .getCurrentGameState().wands.single().slots.single().magicSlots.single().placedMagic shouldBe magicToPutIn
        gameViewModel
            .undoLastAction()
            .addAction(PlaceMagicAction(wand.id, slot.id, Magic(MagicType.GREEN)))
        gameViewModel.getCurrentGameState().wands.single().slots.single().magicSlots.single().placedMagic shouldBe null
    }

    @Test
    fun addNonFittingMagic() {
        val gameViewModel = GameViewModel()
        val (newWand, slot) = getExampleWandWithSingleSlot()
        gameViewModel
            .addAction(AddWandAction(newWand))
            .addAction(
                PlaceMagicAction(newWand.id, slot.id, Magic(MagicType.GREEN))
            ) // not fitting magic type
        gameViewModel.getCurrentGameState().wands.single().slots.single().magicSlots.single().placedMagic shouldBe null
    }

    @Test
    fun newWandAction() {
        val gameViewModel = GameViewModel()
        val (newWand, _) = getExampleWandWithSingleSlot()
        gameViewModel
            .addAction(AddWandAction(newWand))
            .getCurrentGameState().wands.single() shouldBe newWand
    }

    @Test
    fun undoLastAction() {
        val gameViewModel = GameViewModel()
        val (newWand, _) = getExampleWandWithSingleSlot()
        gameViewModel
            .addAction(AddWandAction(newWand))
            .getCurrentGameState().wands.single() shouldBe newWand

        gameViewModel.undoLastAction()
            .getCurrentGameState().wands shouldBe emptyList()
    }
}
