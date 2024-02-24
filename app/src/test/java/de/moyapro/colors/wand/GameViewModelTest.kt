package de.moyapro.colors.wand

import de.moyapro.colors.game.AddWandAction
import de.moyapro.colors.game.GameViewModel
import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.game.PlaceGemAction
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
        val newWand = Wand(Spell("spell", Magic()))
        val placedGem = Gem()
        gameViewModel
            .addAction(AddWandAction(newWand))
            .addAction(PlaceGemAction(newWand.id, placedGem))
            .getCurrentGameState().wands.single().magicSlots.single().full shouldBe true
    }

    @Test
    fun newWandAction() {
        val gameViewModel = GameViewModel()
        val newWand = Wand()
        gameViewModel
            .addAction(AddWandAction(newWand))
            .getCurrentGameState().wands.single() shouldBe newWand
    }

    @Test
    fun undoLastAction() {
        val gameViewModel = GameViewModel()
        val newWand = Wand()
        gameViewModel
            .addAction(AddWandAction(newWand))
            .getCurrentGameState().wands.single() shouldBe newWand

        gameViewModel.undoLastAction()
            .getCurrentGameState().wands shouldBe emptyList()
    }
}
