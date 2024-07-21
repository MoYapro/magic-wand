package de.moyapro.colors.game.actions.fight

import de.moyapro.colors.game.*
import io.kotest.matchers.*
import org.junit.*

internal class ShowTargetSelectionActionTest {

    @Test
    fun `should set targets on hitable enemies`() {
        val state = getExampleGameState()
        val updatedState = ShowTargetSelectionAction(
            ZapAction(
                target = state.currentFight.battleBoard.getEnemies().first().id,
                wandId = state.currentFight.wands.first().id
            )
        ).apply(state).getOrThrow()
        updatedState.currentFight.battleBoard.getEnemies().all { enemy -> enemy.showTarget } shouldBe true
    }
}