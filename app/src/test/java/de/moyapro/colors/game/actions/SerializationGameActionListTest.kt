package de.moyapro.colors.game.actions

import com.fasterxml.jackson.module.kotlin.*
import de.moyapro.colors.game.*
import de.moyapro.colors.util.*
import io.kotest.matchers.equals.*
import io.kotest.matchers.string.*
import org.junit.*

class SerializationGameActionListTest() {

    @Test
    fun `de-serialize action list`() {
        val objectMapper = getConfiguredJson()
        val actionListData = createExampleActionList()
        val json = objectMapper.writeValueAsString(actionListData)
        val deserialized: List<GameAction> = objectMapper.readValue(json)
        deserialized shouldBeEqual actionListData
    }

    @Test
    fun `serialize single and list should have '@type' field`() {
        val objectMapper = getConfiguredJson()
        val actionList = listOf(NoOp())
        val listJson = objectMapper.writeValueAsString(actionList)
        listJson shouldContain """"@type":"NoOp""""
    }
}
