package de.moyapro.colors.game.actions

import android.util.Log
import com.fasterxml.jackson.module.kotlin.readValue
import de.moyapro.colors.game.model.gameState.GameState
import de.moyapro.colors.util.getConfiguredJson
import de.moyapro.colors.wand.getExampleGameState
import io.kotest.matchers.equals.shouldBeEqual
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.BeforeClass
import org.junit.Test

class SerializationWorldStateTest {

    companion object {
        val objectMapper = getConfiguredJson()

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
    fun `de-serialize world state`() {
        val gameState = getExampleGameState()
        val json = objectMapper.writeValueAsString(gameState)
        val deserialized: GameState = objectMapper.readValue(json)
        objectMapper.writeValueAsString(deserialized) shouldBeEqual json // to check if IDs and stuff changed
        deserialized shouldBeEqual gameState // to check if equals is implemented correctly
    }
}