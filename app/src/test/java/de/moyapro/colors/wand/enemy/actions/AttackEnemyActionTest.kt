package de.moyapro.colors.wand.enemy.actions

import android.util.*
import de.moyapro.colors.*
import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.actions.fight.*
import de.moyapro.colors.game.enemy.*
import de.moyapro.colors.game.enemy.actions.*
import de.moyapro.colors.game.model.*
import io.kotest.matchers.*
import io.mockk.*
import org.junit.*

class AttackEnemyActionTest {

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
    fun `created action does damage`() {
        val attackEnemyAction = AttackEnemyAction()
        val enemy = Enemy(health = 1, nextAction = NoOp(), possibleActions = listOf(attackEnemyAction))
        val mageId = MageId(1)
        val exampleWand = createExampleWand(mageId = mageId)
        val mageStartingHealth = 20
        val mage = createExampleMage(mageId = mageId, wandId = exampleWand.id, health = mageStartingHealth)
        val state = MyGameState(
            enemies = listOf(enemy),
            wands = listOf(exampleWand),
            magicToPlay = emptyList(),
            mages = listOf(mage),
            currentTurn = 0,
        )
        val viewModel = GameViewModel(state)
            .addAction(EndTurnAction()) // choose attack as next action
            .addAction(EndTurnAction()) // execute attack action

        viewModel.getCurrentGameState().getOrThrow().findMage(mageId)!!.health shouldNotBe mageStartingHealth
    }
}