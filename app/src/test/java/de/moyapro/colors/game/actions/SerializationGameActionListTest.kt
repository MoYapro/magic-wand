package de.moyapro.colors.game.actions

import com.fasterxml.jackson.module.kotlin.*
import de.moyapro.colors.game.*
import de.moyapro.colors.util.*
import io.kotest.matchers.equals.*
import org.junit.*

class SerializationGameActionListTest() {

    @Test
    fun `de-serialize action list`() {
        val objectMapper = getConfiguredJson()
        val json = objectMapper.writeValueAsString(createExampleActionList())
        val deserialized: List<GameAction> = objectMapper.readValue(json)
        deserialized shouldBeEqual createExampleActionList()
    }
}