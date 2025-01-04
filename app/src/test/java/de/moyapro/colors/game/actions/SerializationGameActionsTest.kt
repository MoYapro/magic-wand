package de.moyapro.colors.game.actions

import de.moyapro.colors.game.createExampleActionList
import de.moyapro.colors.util.getConfiguredJson
import io.kotest.matchers.equals.shouldBeEqual
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.*

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