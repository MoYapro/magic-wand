package de.moyapro.colors.game

import android.util.*
import de.moyapro.colors.game.actions.fight.*
import de.moyapro.colors.game.generators.*
import io.kotest.assertions.throwables.*
import io.mockk.*
import org.junit.*

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