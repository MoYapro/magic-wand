package de.moyapro.colors.game.actions.fight

import de.moyapro.colors.game.model.accessor.*
import de.moyapro.colors.game.*
import io.kotest.matchers.ints.*
import org.junit.*

internal class HitMageActionTest {

    @Test
    fun `should do damage on hit`() {
        val state = getExampleGameState()
        val mageToHit = state.currentFight.mages.first()
        val updatedState = HitMageAction(
            targetMageId = mageToHit.id,
            damage = 5
        ).apply(state).getOrThrow()
        updatedState.currentFight.mages.findMage(mageToHit.id)!!.health shouldBeLessThan mageToHit.health
    }
}