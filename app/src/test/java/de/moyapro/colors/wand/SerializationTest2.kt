package de.moyapro.colors.wand

import com.fasterxml.jackson.module.kotlin.readValue
import de.moyapro.colors.createExampleWand
import de.moyapro.colors.takeTwo.Wand
import de.moyapro.colors.util.getConfiguredJson
import io.kotest.matchers.equality.shouldBeEqualUsingFields
import org.junit.Test

class SerializationTest2 {

    @Test
    fun wandSerialization() {
        val wands = listOf(createExampleWand(), createExampleWand())
        val json = getConfiguredJson().writeValueAsString(wands)
        getConfiguredJson().readValue<List<Wand>>(json)
            .first() shouldBeEqualUsingFields wands.first()
        getConfiguredJson().readValue<List<Wand>>(json).last() shouldBeEqualUsingFields wands.last()
    }

}