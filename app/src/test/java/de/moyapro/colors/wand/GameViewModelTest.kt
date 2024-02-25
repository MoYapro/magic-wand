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
        val newWand = Wand(Spell("spell", Magic()))
        gameViewModel
            .addAction(AddWandAction(newWand))
            .addAction(PlaceMagicAction(newWand.id, Magic(MagicType.SIMPLE)))
            .getCurrentGameState().wands.single().magicSlots.single().full shouldBe true
        gameViewModel.getCurrentGameState().wands.single().magicSlots.single().magic shouldBe Magic(
            MagicType.SIMPLE
        )
        gameViewModel
            .undoLastAction()
            .addAction(PlaceMagicAction(newWand.id, Magic(MagicType.GREEN)))
        gameViewModel.getCurrentGameState().wands.single().magicSlots.single().full shouldBe false
        gameViewModel.getCurrentGameState().wands.single().magicSlots.single().magic shouldBe Magic(
            MagicType.SIMPLE
        )
    }

    @Test
    fun addNonFittingMagic() {
        val gameViewModel = GameViewModel()
        val newWand = Wand(Spell("spell", Magic(MagicType.SIMPLE)))
        gameViewModel
            .addAction(AddWandAction(newWand))
            .addAction(
                PlaceMagicAction(newWand.id, Magic(MagicType.GREEN))
            ) // not fitting magic type
        gameViewModel.getCurrentGameState().wands.single().magicSlots.single().full shouldBe false
        gameViewModel.getCurrentGameState().wands.single().magicSlots.single().magic shouldBe Magic(
            MagicType.SIMPLE
        ) // from the spell requirement
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
