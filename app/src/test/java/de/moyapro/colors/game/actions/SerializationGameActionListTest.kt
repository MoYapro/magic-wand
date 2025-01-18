package de.moyapro.colors.game.actions

import com.fasterxml.jackson.module.kotlin.readValue
import de.moyapro.colors.util.getConfiguredJson
import de.moyapro.colors.wand.createExampleActionList
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.junit.Test

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

    @Test
    fun `deserialize example 1`() {
        val objectMapper = getConfiguredJson()
        val actionsJson =
            """[{"name":"Increase action counter","target":null,"randomSeed":-1,"@type":"IncreaseActionCounterAction"},{"randomSeed":1,"name":"Start fight Action","target":null,"@type":"StartFightAction"}]"""
        val actions = objectMapper.readValue<List<GameAction>>(actionsJson)
        actions.all { it is GameAction } shouldBe true
    }
}
