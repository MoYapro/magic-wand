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
        gameViewModel.getCurrentGameState().getOrThrow() shouldBe MyGameState(emptyList())
    }

    @Test
    fun addGemToWandAction() {
        val gameViewModel = GameViewModel()
        val (wand, slot) = getExampleWandWithSingleSlot()
        val magicToPutIn = Magic(MagicType.SIMPLE)
        gameViewModel
            .addAction(AddWandAction(wand))
            .addAction(PlaceMagicAction(wand.id, slot.id, magicToPutIn))
            .getCurrentGameState().getOrThrow()
            .wands.single().slots.single().magicSlots.single().placedMagic shouldBe magicToPutIn
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
        gameViewModel.getCurrentGameState()
    }

    @Test
    fun newWandAction() {
        val gameViewModel = GameViewModel()
        val (newWand, _) = getExampleWandWithSingleSlot()
        gameViewModel
            .addAction(AddWandAction(newWand))
            .getCurrentGameState().getOrThrow().wands.single() shouldBe newWand
    }

    @Test
    fun undoLastAction() {
        val gameViewModel = GameViewModel()
        val (newWand, _) = getExampleWandWithSingleSlot()
        gameViewModel
            .addAction(AddWandAction(newWand))
            .getCurrentGameState().getOrThrow().wands.single() shouldBe newWand

        gameViewModel.undoLastAction()
            .getCurrentGameState().getOrThrow().wands shouldBe emptyList()
    }
}
