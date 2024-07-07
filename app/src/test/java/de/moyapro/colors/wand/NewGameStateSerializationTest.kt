package de.moyapro.colors.wand

import com.fasterxml.jackson.module.kotlin.*
import de.moyapro.colors.game.model.gameState.*
import de.moyapro.colors.util.*
import io.kotest.matchers.*
import io.kotest.matchers.equals.*
import org.junit.*

internal class NewGameStateSerializationTest {

    @Test
    fun serialization() {
        val newGameState = getExampleGameState()
        val jsonString = getConfiguredJson().writeValueAsString(newGameState)
        val deserializedGameState: NewGameState = getConfiguredJson().readValue(jsonString)
        deserializedGameState shouldEqualSerialized newGameState
        deserializedGameState shouldBe newGameState
    }
}

infix fun <T, U : T> T.shouldEqualSerialized(expected: U?): T {
    getConfiguredJson().writeValueAsString(this) shouldBeEqual getConfiguredJson().writeValueAsString(expected)
    return this
}