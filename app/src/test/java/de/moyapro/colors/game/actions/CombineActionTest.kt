package de.moyapro.colors.wand.actions

import android.util.*
import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.actions.fight.*
import de.moyapro.colors.game.model.*
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
        val magic = Magic(type = MagicType.GREEN)
        val state = getExampleGameState()
        val wandToPlaceMagicIn = state.currentRun.activeWands.first()
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
            .currentRun.activeWands.findWand(wandToPlaceMagicIn.id)!!
            .slots.findSlot(slotToPlaceMagicIn.id)!!
            .spell!!
            .magicSlots.single { it.placedMagic == magic }
            .placedMagic shouldNotBe null

    }

    @Test
    fun `combined faulty action should undo all`() {
        val state = getExampleGameState()
        val wandToEdit = state.currentRun.activeWands.first()
        val magic1 = Magic(type = MagicType.GREEN)
        val magic2 = Magic(type = MagicType.SIMPLE)
        val slotToInsert = wandToEdit.slots.first { it.spell?.name == "Top" }
        val viewModel = GameViewModel(state)
            .addAction(
                CombinedAction(
                    PlaceMagicAction(
                        wandToEdit.id,
                        slotToInsert.id,
                        magicToPlace = magic1
                    ),
                    PlaceMagicAction(
                        wandToEdit.id,
                        slotToInsert.id,
                        magicToPlace = magic2
                    )
                )
            )
        val currentGameState = viewModel.getCurrentGameState()
        currentGameState.isSuccess shouldBe true
        currentGameState.getOrThrow().currentRun.activeWands.findWand(wandToEdit.id)!!.slots.findSlot(slotToInsert.id)!!.spell?.magicSlots?.single() shouldNotBe null

    }


}
