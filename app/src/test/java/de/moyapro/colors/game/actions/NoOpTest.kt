package de.moyapro.colors.game.actions

import de.moyapro.colors.wand.getExampleGameState
import io.kotest.matchers.shouldBe
import org.junit.Test

internal class NoOpTest {
    @Test
    fun `NoOp should do nothing`() {
        val state = getExampleGameState()
        val updatedState = NoOp().apply(state).getOrThrow()
        state shouldBe updatedState
    }
}