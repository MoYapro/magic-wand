package de.moyapro.colors.game.actions.loot

import android.util.*
import de.moyapro.colors.game.*
import de.moyapro.colors.game.model.*
import de.moyapro.colors.game.model.MagicType.SIMPLE
import io.kotest.matchers.*
import io.kotest.matchers.collections.*
import io.mockk.*
import org.junit.*

class AddGeneratorActionTest {

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
    fun `generators are added`() {
        val state = getExampleGameState()
        val stateWithGenerator = AddGeneratorAction().apply(state).getOrThrow()
        stateWithGenerator.currentRun.generators.size shouldBe state.currentRun.generators.size + 1
        stateWithGenerator.currentRun.generators shouldContain MagicGenerator(SIMPLE, 1..1)
    }
}