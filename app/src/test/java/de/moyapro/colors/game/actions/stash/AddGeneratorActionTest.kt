package de.moyapro.colors.game.actions.stash

import android.util.Log
import de.moyapro.colors.game.model.MagicGenerator
import de.moyapro.colors.game.model.MagicType.*
import de.moyapro.colors.wand.getExampleGameState
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.BeforeClass
import org.junit.Test

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