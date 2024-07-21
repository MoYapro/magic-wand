package de.moyapro.colors.game.actions.fight

import android.util.*
import de.moyapro.colors.game.*
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
        val enemyToHit = state.currentFight.battleBoard.getEnemies().first()
        val wandToZap = state.currentFight.wands.first()
        val magicToPlace = state.currentFight.magicToPlay.first()
        val slotForMagic = wandToZap.slots.forMagic(magicToPlace)!!
        val placeMagicAction = PlaceMagicAction(magicToPlace = magicToPlace, wandId = wandToZap.id, slotId = slotForMagic.id)
        val showTargetsAction = ShowTargetSelectionAction(
            ZapAction(
                target = enemyToHit.id,
                wandId = wandToZap.id
            )
        )
        val hitTargetAction = TargetSelectedAction(target = enemyToHit.id)
        gameViewModel.addAction(placeMagicAction)
        gameViewModel.getCurrentGameState().getOrThrow().currentFight
            .wands.findById(wandToZap.id)!!
            .slots.findById(slotForMagic.id)!!
            .hasRequiredMagic() shouldBe true
        gameViewModel.addAction(showTargetsAction)
        gameViewModel.getCurrentGameState().getOrThrow().currentFight
            .wands.findById(wandToZap.id)!!
            .slots.findById(slotForMagic.id)!!
            .hasRequiredMagic() shouldBe true
        gameViewModel.addAction(hitTargetAction)
        gameViewModel.getCurrentGameState().getOrThrow().currentFight
            .wands.findById(wandToZap.id)!!
            .slots.findById(slotForMagic.id)!!
            .hasRequiredMagic() shouldBe false // was used why zapping
        val updatedState = gameViewModel.getCurrentGameState().getOrThrow()
        val hitEnemy = updatedState.currentFight.battleBoard.getEnemies().findById(enemyToHit.id)!!
        hitEnemy.showTarget shouldBe false
        hitEnemy.health shouldBeLessThan enemyToHit.health

    }
}

private fun List<Slot>.forMagic(magicToPlace: Magic): Slot? {
    return this.firstOrNull() { slot -> slot.spell?.magicSlots?.map { it.requiredMagic.type }?.contains(magicToPlace.type) == true }
}
