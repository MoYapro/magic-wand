package de.moyapro.colors.game.actions

import de.moyapro.colors.game.*
import io.kotest.matchers.*
import org.junit.*

internal class NoOpTest {
    @Test
    fun `NoOp should do nothing`() {
        val state = getExampleGameState()
        val updatedState = NoOp().apply(state).getOrThrow()
        state shouldBe updatedState
    }
}