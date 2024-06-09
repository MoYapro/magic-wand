package de.moyapro.colors.wand.actions

import android.util.*
import de.moyapro.colors.*
import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.game.actions.fight.*
import de.moyapro.colors.game.model.*
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
