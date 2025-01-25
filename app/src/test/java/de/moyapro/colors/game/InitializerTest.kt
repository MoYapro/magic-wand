package de.moyapro.colors.game

import android.util.Log
import de.moyapro.colors.game.actions.fight.StartFightAction
import de.moyapro.colors.game.generators.Initializer
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.BeforeClass
import org.junit.Test

class InitializerTest {


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
    fun `should be able to start fight from initial state `() {
        val initialState = Initializer.createInitialGameState()
        shouldNotThrowAny { StartFightAction().apply(initialState) }
    }
}