package de.moyapro.colors.wand.actions

import android.util.*
import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.actions.fight.*
import de.moyapro.colors.game.model.accessor.*
import de.moyapro.colors.wand.*
import io.kotest.matchers.*
import io.mockk.*
import org.junit.*

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
        val viewModel = GameViewModel(state)
            .addAction(
                CombinedAction(
                    PlaceMagicAction(
                        wandToPlaceMagicIn.id,
                        slotToPlaceMagicIn.id,
                        magicToPlace = magic
                    ),
                )
            )
        viewModel.getCurrentGameState().getOrThrow()
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
        val viewModel = GameViewModel(state)
        viewModel.getCurrentGameState().getOrThrow().currentFight.wands.findWand(wandToEdit.id)!!.slots.findSlot(slotToInsert1.id)!!.spell?.magicSlots?.first()?.placedMagic shouldBe null
        viewModel.getCurrentGameState().getOrThrow().currentFight.wands.findWand(wandToEdit.id)!!.slots.findSlot(slotToInsert2.id)!!.spell?.magicSlots?.first()?.placedMagic shouldBe null
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
        val currentGameState = viewModel.getCurrentGameState()
        currentGameState.isSuccess shouldBe true
        currentGameState.getOrThrow().currentFight.wands.findWand(wandToEdit.id)!!.slots.findSlot(slotToInsert1.id)!!.spell?.magicSlots?.first()?.placedMagic shouldNotBe null
        currentGameState.getOrThrow().currentFight.wands.findWand(wandToEdit.id)!!.slots.findSlot(slotToInsert2.id)!!.spell?.magicSlots?.first()?.placedMagic shouldNotBe null
        viewModel.addAction(UndoAction)
        viewModel.getCurrentGameState().getOrThrow().currentFight.wands.findWand(wandToEdit.id)!!.slots.findSlot(slotToInsert1.id)!!.spell?.magicSlots?.first()?.placedMagic shouldBe null
        viewModel.getCurrentGameState().getOrThrow().currentFight.wands.findWand(wandToEdit.id)!!.slots.findSlot(slotToInsert2.id)!!.spell?.magicSlots?.first()?.placedMagic shouldBe null

    }
}
