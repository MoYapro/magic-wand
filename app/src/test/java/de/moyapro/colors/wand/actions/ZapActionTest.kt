package de.moyapro.colors.wand.actions

import android.util.*
import de.moyapro.colors.*
import de.moyapro.colors.game.*
import de.moyapro.colors.game.actions.*
import de.moyapro.colors.takeTwo.*
import de.moyapro.colors.wand.*
import io.kotest.matchers.*
import io.mockk.*
import org.junit.*

class ZapActionTest {

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
    fun `zapping a wand damages enemy`() {
        val magic = Magic(type = MagicType.GREEN)
        val exampleWand = Wand(
            slots = listOf(
                Slot(
                    spell = Spell(
                        name = "Top",
                        magicSlots = listOf(MagicSlot(requiredMagic = magic.copy()))
                    ),
                    level = 2,
                    power = 1
                ),
            )
        )
        val startingHealth = 10
        val enemy = createExampleEnemy(health = startingHealth)
        val state = MyGameState(
            enemies = listOf(enemy),
            wands = listOf(exampleWand),
            magicToPlay = listOf(
                magic.copy(),
            ),
            currentTurn = 0,
        )
        val viewModel = GameViewModel(state)
            .addAction(
                PlaceMagicAction(
                    exampleWand.id,
                    exampleWand.slots.single { it.spell?.name == "Top" }.id,
                    magicToPlace = magic
                )
            )
            .addAction(ZapAction(exampleWand.id))
            .addAction(TargetSelectedAction(enemy.id))
        viewModel.getCurrentGameState()
            .getOrThrow().enemies.first().health shouldBe (startingHealth - 1)

    }

    @Test
    fun `dead mage cannot zap wand`() {
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
                PlaceMagicAction(
                    exampleWand.id,
                    exampleWand.slots.first { it.spell?.name == "Top" }.id,
                    magicToPlace = magic
                )
            )
            .addAction(ZapAction(exampleWand.id))
        viewModel.getCurrentGameState()
            .getOrThrow().enemies.single().health shouldBe (startingHealth)

    }

    @Test
    fun `zapping a wand kills enemy`() {
        val exampleWand = createExampleWand()
        val magic = Magic(type = MagicType.GREEN)
        val startingHealth = 1
        val enemy = createExampleEnemy(health = startingHealth)
        val exampleMage = createExampleMage(mageId = MageId(1))
        val state = MyGameState(
            enemies = listOf(enemy),
            wands = listOf(exampleWand),
            mages = listOf(exampleMage),
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
                PlaceMagicAction(
                    exampleWand.id,
                    exampleWand.slots.first { it.spell?.name == "Top" }.id,
                    magicToPlace = magic
                )
            )
            .addAction(ZapAction(exampleWand.id))
            .addAction(TargetSelectedAction(enemy.id))

        viewModel.getCurrentGameState()
            .getOrThrow().enemies shouldBe emptyList()

    }

}
