package de.moyapro.colors.game.actions

import android.util.Log
import de.moyapro.colors.game.GameViewModel
import de.moyapro.colors.game.actions.fight.PlaceMagicAction
import de.moyapro.colors.game.model.accessor.findSlot
import de.moyapro.colors.game.model.accessor.findWand
import de.moyapro.colors.wand.getExampleGameState
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.BeforeClass
import org.junit.Test

class CombineActionTest {

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
    fun `combined single action`() {
        val state = getExampleGameState()
        val magic = state.currentFight.magicToPlay.first()
        val wandToPlaceMagicIn = state.currentFight.wands.first()
        val slotToPlaceMagicIn = wandToPlaceMagicIn.slots.first()
        CombinedAction(
            PlaceMagicAction(
                wandToPlaceMagicIn.id,
                slotToPlaceMagicIn.id,
                magicToPlace = magic
            ),
        )
            .apply(state).getOrThrow()
            .currentFight.wands.findWand(wandToPlaceMagicIn.id)!!
            .slots.findSlot(slotToPlaceMagicIn.id)!!
            .spell!!
            .magicSlots.single { it.placedMagic == magic }
            .placedMagic shouldNotBe null

    }

    @Test
    fun `combined faulty action should undo all`() {
        val state = getExampleGameState()
        val wandToEdit = state.currentFight.wands.first()
        val (slotToInsert1, slotToInsert2) = wandToEdit.slots
        val magic1 = state.currentFight.magicToPlay.first { slotToInsert1.spell!!.magicSlots.first().requiredMagic.type == it.type }
        val magic2 = state.currentFight.magicToPlay.last { slotToInsert2.spell!!.magicSlots.first().requiredMagic.type == it.type }
        val viewModel = GameViewModel(initialState = state, loadActions = { emptyList() }, saveState = { _, _ -> Unit }, saveActions = {})
        viewModel.uiState.value.getOrThrow().currentFight.wands.findWand(wandToEdit.id)!!.slots.findSlot(slotToInsert1.id)!!.spell?.magicSlots?.first()?.placedMagic shouldBe null
        viewModel.uiState.value.getOrThrow().currentFight.wands.findWand(wandToEdit.id)!!.slots.findSlot(slotToInsert2.id)!!.spell?.magicSlots?.first()?.placedMagic shouldBe null
        viewModel
            .addAction(
                CombinedAction(
                    PlaceMagicAction(
                        wandToEdit.id,
                        slotToInsert1.id,
                        magicToPlace = magic1
                    ),
                    PlaceMagicAction(
                        wandToEdit.id,
                        slotToInsert2.id,
                        magicToPlace = magic2
                    )
                )
            )
        val currentGameState = viewModel.uiState.value
        currentGameState.isSuccess shouldBe true
        currentGameState.getOrThrow().currentFight.wands.findWand(wandToEdit.id)!!.slots.findSlot(slotToInsert1.id)!!.spell?.magicSlots?.first()?.placedMagic shouldNotBe null
        currentGameState.getOrThrow().currentFight.wands.findWand(wandToEdit.id)!!.slots.findSlot(slotToInsert2.id)!!.spell?.magicSlots?.first()?.placedMagic shouldNotBe null
        viewModel.addAction(UndoAction)
        viewModel.uiState.value.getOrThrow().currentFight.wands.findWand(wandToEdit.id)!!.slots.findSlot(slotToInsert1.id)!!.spell?.magicSlots?.first()?.placedMagic shouldBe null
        viewModel.uiState.value.getOrThrow().currentFight.wands.findWand(wandToEdit.id)!!.slots.findSlot(slotToInsert2.id)!!.spell?.magicSlots?.first()?.placedMagic shouldBe null
    }
}
