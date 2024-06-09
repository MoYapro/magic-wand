package de.moyapro.colors.wand

import android.util.*
import de.moyapro.colors.*
import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.fight.*
import de.moyapro.colors.game.actions.loot.*
import de.moyapro.colors.game.generators.*
import de.moyapro.colors.game.model.*
import io.kotest.matchers.*
import io.kotest.matchers.collections.*
import io.mockk.*
import org.junit.*

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
            enemies shouldHaveSize 1
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
            .addAction(AddWandAction(wand, MageId(0)))
            .addAction(PlaceMagicAction(wand.id, slot.id, magicToPutIn))
            .getCurrentGameState().getOrThrow()
            .wands.last().slots.single().spell?.magicSlots?.single()?.placedMagic shouldBe magicToPutIn
        gameViewModel.getCurrentGameState().getOrThrow().magicToPlay shouldBe emptyList()
    }

    @Test
    fun addNonFittingMagic() {
        val gameViewModel = GameViewModel()
        val (newWand, slot) = getExampleWandWithSingleSlot()
        gameViewModel
            .addAction(AddWandAction(newWand, MageId(0)))
            .addAction(
                PlaceMagicAction(newWand.id, slot.id, Magic(type = MagicType.GREEN))
            ) // not fitting magic type
        gameViewModel.getCurrentGameState()
    }

    @Test
    fun newWandAction() {
        val initialState = StartFightFactory.setupFightStage()
        val newWand = createExampleWand()

        val finalState = AddWandAction(newWand, MageId(0)).apply(initialState).getOrThrow()
        finalState.wands.single().id shouldBe newWand.id
    }
}
