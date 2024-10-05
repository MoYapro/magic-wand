package de.moyapro.colors.game

import com.fasterxml.jackson.module.kotlin.*
import de.moyapro.colors.game.model.gameState.*
import de.moyapro.colors.util.*
import io.kotest.matchers.*
import io.kotest.matchers.equals.*
import org.junit.*

internal class GameStateSerializationTest {

    @Test
    fun serialization() {
        val newGameState = getExampleGameState()
        val jsonString = getConfiguredJson().writeValueAsString(newGameState)
        val deserializedGameState: GameState = getConfiguredJson().readValue(jsonString)
        deserializedGameState shouldEqualSerialized newGameState
        deserializedGameState shouldBe newGameState
    }
}

infix fun <T, U : T> T.shouldEqualSerialized(expected: U?): T {
    getConfiguredJson().writeValueAsString(this) shouldBeEqual getConfiguredJson().writeValueAsString(expected)
    return this
}