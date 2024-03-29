package de.moyapro.colors.wand.actions

import android.util.Log
import de.moyapro.colors.createExampleEnemy
import de.moyapro.colors.createExampleMage
import de.moyapro.colors.createExampleWand
import de.moyapro.colors.game.GameViewModel
import de.moyapro.colors.game.MyGameState
import de.moyapro.colors.game.actions.PlaceMagicAction
import de.moyapro.colors.game.actions.TargetSelectedAction
import de.moyapro.colors.game.actions.ZapAction
import de.moyapro.colors.takeTwo.Slot
import de.moyapro.colors.takeTwo.Wand
import de.moyapro.colors.wand.Magic
import de.moyapro.colors.wand.MagicSlot
import de.moyapro.colors.wand.MagicType
import de.moyapro.colors.wand.Spell
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.BeforeClass
import org.junit.Test

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
                    magicSlots = listOf(MagicSlot(requiredMagic = magic.copy())),
                    spell = Spell(name = "Top"),
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
            .addAction(TargetSelectedAction(enemy))
        viewModel.getCurrentGameState()
            .getOrThrow().enemies.first().health shouldBe (startingHealth - 1)

    }

    @Test
    fun `dead mage cannot zap wand`() {
        val exampleWand = createExampleWand()
        val exampleMage = createExampleMage(wandId = exampleWand.id)
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
            .getOrThrow().enemies.first().health shouldBe (startingHealth - 1)

    }

    @Test
    fun `zapping a wand kills enemy`() {
        val exampleWand = createExampleWand()
        val magic = Magic(type = MagicType.GREEN)
        val startingHealth = 1
        val enemy = createExampleEnemy(health = startingHealth)
        val exampleMage = createExampleMage()
        val state = MyGameState(
            enemies = listOf(enemy),
            wands = listOf(exampleWand),
            mages = listOf(exampleMage),
                    magicToPlay = listOf (
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
            .addAction(TargetSelectedAction(enemy))

        viewModel.getCurrentGameState()
            .getOrThrow().enemies shouldBe emptyList()

    }

}
