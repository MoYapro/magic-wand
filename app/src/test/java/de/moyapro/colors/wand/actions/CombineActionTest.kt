package de.moyapro.colors.wand.actions

import android.util.Log
import de.moyapro.colors.createExampleEnemy
import de.moyapro.colors.createExampleWand
import de.moyapro.colors.game.GameViewModel
import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.game.actions.CombinedAction
import de.moyapro.colors.game.actions.PlaceMagicAction
import de.moyapro.colors.wand.Magic
import de.moyapro.colors.wand.MagicType
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
        val exampleWand = createExampleWand()
        val magic = Magic(type = MagicType.GREEN)
        val startingHealth = 10
        val exampleEnemy = createExampleEnemy(health = startingHealth)
        val state = MyGameState(
            enemies = listOf(exampleEnemy),
            wands = listOf(exampleWand),
            magicToPlay = listOf(
                magic,
                Magic(type = MagicType.GREEN),
                Magic(type = MagicType.GREEN),
                Magic(type = MagicType.GREEN)
            ),
            currentTurn = 0,
        )
        val viewModel = GameViewModel(state)
            .addAction(
                CombinedAction(
                    PlaceMagicAction(
                        exampleWand.id,
                        exampleWand.slots.first { it.spell?.name == "Top" }.id,
                        magicToPlace = magic
                    ),
                )
            )
        viewModel.getCurrentGameState().getOrThrow()
            .wands.single()
            .slots.first { it.spell?.name == "Top" }
            .spell!!
            .magicSlots.single { it.placedMagic == magic }
            .placedMagic shouldNotBe null

    }

    @Test
    fun `combined faulty action should undo all`() {
        val exampleWand = createExampleWand()
        val magic = Magic(type = MagicType.GREEN)
        val startingHealth = 10
        val exampleEnemy = createExampleEnemy(health = startingHealth)
        val state = MyGameState(
            enemies = listOf(exampleEnemy),
            wands = listOf(exampleWand),
            magicToPlay = listOf(
                magic,
                Magic(type = MagicType.GREEN),
                Magic(type = MagicType.GREEN),
                Magic(type = MagicType.GREEN)
            ),
            currentTurn = 0,
        )
        val viewModel = GameViewModel(state)
            .addAction(
                CombinedAction(
                    PlaceMagicAction(
                        exampleWand.id,
                        exampleWand.slots.first { it.spell?.name == "Top" }.id,
                        magicToPlace = magic
                    ),
                    PlaceMagicAction(
                        exampleWand.id,
                        exampleWand.slots.first { it.spell?.name == "Top" }.id,
                        magicToPlace = magic
                    )
                )
            )
        val currentGameState = viewModel.getCurrentGameState()
        currentGameState.isSuccess shouldBe true
        currentGameState.getOrThrow().wands.single().slots.first { it.spell?.name == "Top" }.spell?.magicSlots?.single() shouldNotBe null

    }


}
