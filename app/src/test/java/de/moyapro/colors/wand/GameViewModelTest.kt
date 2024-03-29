package de.moyapro.colors.wand

import android.util.Log
import de.moyapro.colors.game.GameViewModel
import de.moyapro.colors.game.actions.AddWandAction
import de.moyapro.colors.game.actions.PlaceMagicAction
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.BeforeClass
import org.junit.Test

internal class GameViewModelTest {

    companion object {
        @BeforeClass
        @JvmStatic
        fun setup() {
            mockkStatic(Log::class)
            every { Log.d(any(), any()) } returns 1
            every { Log.e(any(), any()) } returns 1
            every { Log.i(any(), any()) } returns 1
        }
    }


    @Test
    fun initEmpty() {
        val gameViewModel = GameViewModel()
        with(gameViewModel.getCurrentGameState().getOrThrow()) {
            enemies shouldHaveSize 2
            wands shouldHaveSize 1
            magicToPlay shouldHaveSize 1
        }
    }

    @Test
    fun addMagicToWandAction() {
        val gameViewModel = GameViewModel()
        val (wand, slot) = getExampleWandWithSingleSlot()
        val magicToPutIn = gameViewModel.getCurrentGameState().getOrThrow().magicToPlay.first()
        gameViewModel
            .addAction(AddWandAction(wand))
            .addAction(PlaceMagicAction(wand.id, slot.id, magicToPutIn))
            .getCurrentGameState().getOrThrow()
            .wands.last().slots.single().magicSlots.single().placedMagic shouldBe magicToPutIn
        gameViewModel.getCurrentGameState().getOrThrow().magicToPlay shouldBe emptyList()
    }

    @Test
    fun addNonFittingMagic() {
        val gameViewModel = GameViewModel()
        val (newWand, slot) = getExampleWandWithSingleSlot()
        gameViewModel
            .addAction(AddWandAction(newWand))
            .addAction(
                PlaceMagicAction(newWand.id, slot.id, Magic(type = MagicType.GREEN))
            ) // not fitting magic type
        gameViewModel.getCurrentGameState()
    }

    @Test
    fun newWandAction() {
        val gameViewModel = GameViewModel()
        val (newWand, _) = getExampleWandWithSingleSlot()
        gameViewModel
            .addAction(AddWandAction(newWand))
            .getCurrentGameState().getOrThrow().wands.last() shouldBe newWand
    }

    @Test
    fun undoLastAction() {
        val gameViewModel = GameViewModel()
        val (newWand, _) = getExampleWandWithSingleSlot()
        gameViewModel
            .addAction(AddWandAction(newWand))
            .getCurrentGameState().getOrThrow().wands.last() shouldBe newWand
        gameViewModel.getCurrentGameState().getOrThrow().wands shouldHaveSize 2

        gameViewModel.undoLastAction()
            .getCurrentGameState().getOrThrow().wands shouldHaveSize 1
    }
}
