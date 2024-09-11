package de.moyapro.colors.game.actions

import android.util.*
import com.fasterxml.jackson.module.kotlin.*
import de.moyapro.colors.game.generators.*
import de.moyapro.colors.game.model.gameState.*
import de.moyapro.colors.util.*
import io.kotest.matchers.equals.*
import io.mockk.*
import org.junit.*

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
        val gameState = StartFightFactory.setupFightStage()
        val json = objectMapper.writeValueAsString(gameState)
        val deserialized: NewGameState = objectMapper.readValue(json)
        objectMapper.writeValueAsString(deserialized) shouldBeEqual json // to check if IDs and stuff changed
        deserialized shouldBeEqual gameState // to check if equals is implemented correctly
    }
}