package de.moyapro.colors.game.actions.fight

import android.util.*
import de.moyapro.colors.*
import de.moyapro.colors.game.*
import de.moyapro.colors.game.enemy.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.accessor.*
import io.kotest.matchers.*
import io.kotest.matchers.ints.*
import io.mockk.*
import org.junit.*

internal class TargetSelectionActionTest {

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
    fun `should execute action on selected enemy`() {
        val gameViewModel = GameViewModel(getExampleGameState())
        val state = gameViewModel.getCurrentGameState().getOrThrow()
        val fieldToHitId = state.currentFight.battleBoard.fields.last().id
        val wandToZap = state.currentFight.wands.first()
        val magicToPlace = state.currentFight.magicToPlay.first()
        val slotForMagic = wandToZap.slots.forMagic(magicToPlace)!!
        val placeMagicAction = PlaceMagicAction(magicToPlace = magicToPlace, wandId = wandToZap.id, slotId = slotForMagic.id)
        val showTargetsAction = ShowTargetSelectionAction(ZapAction(target = fieldToHitId, wandId = wandToZap.id))
        val selectTargetAction = TargetSelectedAction(targetFieldId = fieldToHitId)
        gameViewModel.addAction(placeMagicAction)
        gameViewModel.addAction(showTargetsAction)
        gameViewModel.addAction(selectTargetAction)
        val updatedState = gameViewModel.getCurrentGameState().getOrThrow()
        val hitField = updatedState.currentFight.battleBoard.fields.findById(fieldToHitId)!!
        hitField.showTarget shouldBe false
        hitField.enemy!!.health shouldBeLessThan state.currentFight.battleBoard.fields.findById(fieldToHitId)!!.enemy!!.health
    }

    @Test
    fun `cannot target enemy in the backrow`() {
        val exampleGameState = getExampleGameState()
        val stateWithEnemies = exampleGameState.copy(currentFight = exampleGameState.currentFight.copy(battleBoard = createExampleBattleBoardFilledWith(TargetDummy(10))))
        val wandToZap = stateWithEnemies.currentFight.wands.first()
        val showTargetsAction = ShowTargetSelectionAction(ZapAction(wandId = wandToZap.id))
        val stateWithTargets = showTargetsAction.apply(stateWithEnemies).getOrThrow()
        stateWithTargets.currentFight.battleBoard
            .fields.filterIndexed { index, _ -> index >= 10 }
            .map { field -> field.showTarget } shouldBe List(size = 5) { true }
        stateWithTargets.currentFight.battleBoard
            .fields.filterIndexed { index, _ -> index < 10 }
            .map { field -> field.showTarget } shouldBe List(size = 10) { false }
    }
}

private fun List<Slot>.forMagic(magicToPlace: Magic): Slot? {
    return this.firstOrNull() { slot -> slot.spell?.magicSlots?.map { it.requiredMagic.type }?.contains(magicToPlace.type) == true }
}
