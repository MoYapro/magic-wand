package de.moyapro.colors.game.actions

import de.moyapro.colors.game.*
import de.moyapro.colors.util.*
import io.kotest.matchers.equals.*
import org.junit.*
import org.junit.runner.*
import org.junit.runners.*
import org.junit.runners.Parameterized.Parameters

@RunWith(Parameterized::class)
class SerializationGameActionsTest(private val value: Any) {

    companion object {
        @Parameters(name = "{index}: {0}")
        @JvmStatic
        fun data(): List<GameAction> = createExampleActionList()
    }

    @Test
    fun `de-serialize single types`() {
        val objectMapper = getConfiguredJson()
        val internalValue: GameAction = value as GameAction
        val json = objectMapper.writeValueAsString(internalValue)
        val deserialized: GameAction = objectMapper.readValue(json, GameAction::class.java)
        deserialized shouldBeEqual internalValue
    }
}